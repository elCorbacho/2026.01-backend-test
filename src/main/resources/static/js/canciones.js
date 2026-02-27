(function () {
    const API = '/api/canciones';
    let allCanciones = [];
    const modalEl  = document.getElementById('modalForm');
    const modal     = new bootstrap.Modal(modalEl);
    const toastEl   = document.getElementById('appToast');
    const bsToast   = new bootstrap.Toast(toastEl, { delay: 2800 });

    // â”€â”€ Theme â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    const html   = document.documentElement;
    const toggle = document.getElementById('themeToggle');
    const saved  = localStorage.getItem('theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const initTheme = saved || (prefersDark ? 'dark' : 'light');

    function applyTheme(t) {
        html.setAttribute('data-bs-theme', t);
        localStorage.setItem('theme', t);
    }
    applyTheme(initTheme);
    toggle.addEventListener('click', () =>
        applyTheme(html.getAttribute('data-bs-theme') === 'dark' ? 'light' : 'dark')
    );

    // â”€â”€ Toast helper â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    function toast(msg, ok = true) {
        toastEl.className = 'toast align-items-center border-0 ' + (ok ? 'text-bg-success' : 'text-bg-danger');
        document.getElementById('toastMsg').textContent = msg;
        bsToast.show();
    }

    // â”€â”€ Format duration â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    function fmtDur(s) {
        if (!s) return 'â€”';
        const m = Math.floor(s / 60), r = s % 60;
        return m + ':' + String(r).padStart(2, '0');
    }

    // â”€â”€ Render table â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    function renderTable(data) {
        const tbody = document.getElementById('tableBody');
        const empty = document.getElementById('emptyState');
        const count = document.getElementById('countBadge');
        document.getElementById('loadingRow')?.remove();

        tbody.innerHTML = '';
        if (!data.length) {
            empty.classList.remove('d-none');
            count.textContent = '';
            return;
        }
        empty.classList.add('d-none');
        count.textContent = data.length + ' canciÃ³n' + (data.length !== 1 ? 'es' : '');

        data.forEach(c => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td style="color:var(--muted);font-size:.78rem;font-family:'JetBrains Mono',monospace;">${c.id}</td>
                <td class="fw-semibold">${esc(c.titulo)}</td>
                <td>${esc(c.artista)}</td>
                <td>${c.duracion ? '<span class="dur-pill">' + fmtDur(c.duracion) + '</span>' : '<span style="color:var(--muted)">â€”</span>'}</td>
                <td>${c.genero ? '<span class="genero-pill">' + esc(c.genero) + '</span>' : '<span style="color:var(--muted)">â€”</span>'}</td>
                <td>
                    <div class="d-flex gap-1">
                        <button class="btn-icon" title="Editar" onclick="openEdit(${c.id})"><i class="bi bi-pencil"></i></button>
                        <button class="btn-icon danger" title="Eliminar" onclick="eliminar(${c.id}, '${esc(c.titulo)}')"><i class="bi bi-trash3"></i></button>
                    </div>
                </td>`;
            tbody.appendChild(tr);
        });
    }

    function esc(s) { return String(s ?? '').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;'); }

    // â”€â”€ Load all â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.cargarCanciones = function () {
        const tbody = document.getElementById('tableBody');
        tbody.innerHTML = '<tr id="loadingRow"><td colspan="6"><span class="spinner-border spinner-border-sm text-success me-2"></span>Cargandoâ€¦</td></tr>';
        document.getElementById('emptyState').classList.add('d-none');
        document.getElementById('searchInput').value = '';

        fetch(API)
            .then(r => r.json())
            .then(res => { allCanciones = res.data || []; renderTable(allCanciones); })
            .catch(() => toast('Error al cargar canciones', false));
    };

    // â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.onSearch = function (val) {
        if (!val.trim()) { renderTable(allCanciones); return; }
        fetch(API + '/buscar?artista=' + encodeURIComponent(val))
            .then(r => r.json())
            .then(res => renderTable(res.data || []))
            .catch(() => {});
    };

    // â”€â”€ Open create modal â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.openCreate = function () {
        document.getElementById('modalTitle').textContent = 'Nueva canciÃ³n';
        document.getElementById('editId').value = '';
        document.getElementById('cancionForm').classList.remove('was-validated');
        ['fTitulo','fArtista','fDuracion','fGenero'].forEach(id => document.getElementById(id).value = '');
    };

    // â”€â”€ Open edit modal â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.openEdit = function (id) {
        const c = allCanciones.find(x => x.id === id);
        if (!c) return;
        document.getElementById('modalTitle').textContent = 'Editar canciÃ³n';
        document.getElementById('editId').value = id;
        document.getElementById('fTitulo').value  = c.titulo  ?? '';
        document.getElementById('fArtista').value = c.artista ?? '';
        document.getElementById('fDuracion').value = c.duracion ?? '';
        document.getElementById('fGenero').value  = c.genero  ?? '';
        document.getElementById('cancionForm').classList.remove('was-validated');
        modal.show();
    };

    // â”€â”€ Save (create or update) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.guardar = function () {
        const form = document.getElementById('cancionForm');
        form.classList.add('was-validated');
        if (!form.checkValidity()) return;

        const id      = document.getElementById('editId').value;
        const payload = {
            titulo:   document.getElementById('fTitulo').value.trim(),
            artista:  document.getElementById('fArtista').value.trim(),
            duracion: document.getElementById('fDuracion').value ? +document.getElementById('fDuracion').value : null,
            genero:   document.getElementById('fGenero').value.trim() || null
        };
        const isEdit  = !!id;
        const url     = isEdit ? API + '/' + id : API;
        const method  = isEdit ? 'PUT' : 'POST';

        const btn = document.getElementById('btnGuardar');
        btn.disabled = true;
        btn.innerHTML = '<span class="spinner-border spinner-border-sm me-1"></span>Guardandoâ€¦';

        fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) })
            .then(r => r.json())
            .then(res => {
                if (res.success) {
                    modal.hide();
                    toast(isEdit ? 'CanciÃ³n actualizada' : 'CanciÃ³n creada');
                    cargarCanciones();
                } else {
                    toast(res.message || 'Error al guardar', false);
                }
            })
            .catch(() => toast('Error de conexiÃ³n', false))
            .finally(() => { btn.disabled = false; btn.innerHTML = '<i class="bi bi-check-lg me-1"></i>Guardar'; });
    };

    // â”€â”€ Delete â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    window.eliminar = function (id, titulo) {
        if (!confirm('Â¿Eliminar "' + titulo + '"? (soft delete)')) return;
        fetch(API + '/' + id, { method: 'DELETE' })
            .then(r => r.json())
            .then(res => {
                if (res.success) { toast('CanciÃ³n eliminada'); cargarCanciones(); }
                else toast(res.message || 'Error al eliminar', false);
            })
            .catch(() => toast('Error de conexiÃ³n', false));
    };

    // â”€â”€ Init â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    cargarCanciones();
})();

