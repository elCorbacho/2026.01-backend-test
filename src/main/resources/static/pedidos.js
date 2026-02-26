const apiRoot = '/api/pedidos';

async function fetchPedidos() {
  const res = await fetch(apiRoot);
  if (!res.ok) throw new Error('Error cargando pedidos');
  const body = await res.json();
  return body.data || [];
}

function renderPedidos(list){
  const ul = document.getElementById('pedidosList');
  const placeholder = document.getElementById('listPlaceholder');
  ul.innerHTML = '';
  if(!list.length){ placeholder.textContent = 'No hay pedidos'; return }
  placeholder.textContent = '';
  list.forEach((p, i) => {
    const li = document.createElement('li');
    li.classList.add('reveal');
    const left = document.createElement('div');
    left.className = 'left';
    left.innerHTML = `<strong>${escapeHtml(p.clienteNombre)}</strong><div class="muted">${(p.items||[]).length} ítems • $${Number(p.total).toFixed(2)}</div>`;
    const right = document.createElement('div');
    right.textContent = new Date(p.createdAt).toLocaleString();
    const badge = document.createElement('div');
    badge.className = 'badge badge-floating';
    badge.textContent = ((p.items||[]).length) + '×';
    li.append(left, badge, right);
    ul.append(li);
    // staggered reveal
    setTimeout(()=> li.classList.add('show'), 80 * i);
  })
}

function escapeHtml(s){ if(!s) return ''; return s.replace(/[&<>"']/g, c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])) }

async function loadAndRender(){
  try{
    const list = await fetchPedidos();
    renderPedidos(list);
  }catch(e){
    document.getElementById('listPlaceholder').textContent = 'No se pudieron cargar pedidos';
  }
}

document.addEventListener('DOMContentLoaded', ()=>{
  loadAndRender();

  const form = document.getElementById('pedidoForm');
  const errorEl = document.getElementById('formError');
  form.addEventListener('submit', async (ev)=>{
    ev.preventDefault(); errorEl.textContent = '';
    const clienteNombre = document.getElementById('clienteNombre').value.trim();
    const total = parseFloat(document.getElementById('total').value);
    const itemsRaw = document.getElementById('items').value.trim();
    const items = itemsRaw.split(/\r?\n/).map(s=>s.trim()).filter(Boolean);

    // Client-side validations mirroring DTO
    if(clienteNombre.length < 2){ errorEl.textContent = 'El nombre debe tener al menos 2 caracteres'; return }
    if(Number.isNaN(total) || total < 0){ errorEl.textContent = 'El total debe ser un número >= 0'; return }
    if(items.length === 0){ errorEl.textContent = 'Debes añadir al menos un ítem'; return }

    try{
      const res = await fetch(apiRoot, {
        method: 'POST', headers: {'Content-Type':'application/json'},
        body: JSON.stringify({clienteNombre, total, items})
      });

      const body = await res.json();
      if(!res.ok){
        errorEl.textContent = body?.message || 'Error al crear pedido';
        return;
      }

      form.reset();
      loadAndRender();
    }catch(err){ errorEl.textContent = 'Error de red al crear pedido' }
  });

  document.getElementById('limpiar').addEventListener('click', ()=>{ document.getElementById('pedidoForm').reset(); document.getElementById('formError').textContent = '' })
});
