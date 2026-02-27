(function(){
    // Scroll progress
    window.addEventListener('scroll', function(){
        var s = document.documentElement, b = document.body;
        var pct = (s.scrollTop||b.scrollTop) / ((s.scrollHeight||b.scrollHeight) - s.clientHeight) * 100;
        document.getElementById('scroll-progress').style.width = pct + '%';
    });

    // Back to top
    var bt = document.getElementById('backTop');
    window.addEventListener('scroll', function(){ bt.classList.toggle('show', window.scrollY > 300); });

    // Theme toggle
    (function(){
        var html = document.documentElement;
        var btn  = document.getElementById('theme-btn');
        var icon = btn.querySelector('i');
        var saved = localStorage.getItem('theme');
        var prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        var current = saved || (prefersDark ? 'dark' : 'light');
        function applyTheme(t){
            html.setAttribute('data-bs-theme', t);
            icon.className = t === 'dark' ? 'bi bi-moon-stars-fill' : 'bi bi-sun-fill';
            localStorage.setItem('theme', t);
        }
        applyTheme(current);
        btn.addEventListener('click', function(){
            applyTheme(html.getAttribute('data-bs-theme') === 'dark' ? 'light' : 'dark');
        });
    })();

    // Copy to clipboard
    var toast = new bootstrap.Toast(document.getElementById('copyToast'), { delay: 2200 });
    window.copyCode = function(id, btn){
        navigator.clipboard.writeText(document.getElementById(id).innerText).then(function(){
            btn.classList.add('copied');
            btn.innerHTML = '<i class="bi bi-check2 me-1"></i>Copiado';
            toast.show();
            setTimeout(function(){ btn.classList.remove('copied'); btn.innerHTML = '<i class="bi bi-copy me-1"></i>Copiar'; }, 2500);
        });
    };
})();

