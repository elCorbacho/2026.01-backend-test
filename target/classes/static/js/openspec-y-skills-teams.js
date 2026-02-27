(function(){
    // â”€â”€ Scroll progress â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var prog = document.getElementById('scroll-progress');
    window.addEventListener('scroll', function(){
        var tot = document.documentElement.scrollHeight - window.innerHeight;
        prog.style.width = (tot > 0 ? (window.scrollY / tot * 100) : 0) + '%';
    }, {passive:true});

    // â”€â”€ Back to top â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var btt = document.getElementById('backTop');
    window.addEventListener('scroll', function(){ btt.classList.toggle('show', window.scrollY > 300); }, {passive:true});

    // â”€â”€ Active nav â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var navLinks = document.querySelectorAll('#navbarDesktop .nav-link');
    var secs = ['openspec','skills-teams','comparativa','integracion'].map(function(id){ return document.getElementById(id); }).filter(Boolean);
    secs.forEach(function(s){ new IntersectionObserver(function(entries){ entries.forEach(function(e){ if(e.isIntersecting){ navLinks.forEach(function(l){ l.classList.remove('active'); }); var m = document.querySelector('#navbarDesktop a[href="#'+e.target.id+'"]'); if(m) m.classList.add('active'); } }); }, { rootMargin: '-50% 0px -45% 0px' }).observe(s); });

    // â”€â”€ Scroll-reveal â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var revIO = new IntersectionObserver(function(entries){
        entries.forEach(function(e){ if(e.isIntersecting){ e.target.classList.add('visible'); revIO.unobserve(e.target); } });
    }, { threshold: 0.1 });
    document.querySelectorAll('.reveal').forEach(function(el){ revIO.observe(el); });

    // â”€â”€ Copy to clipboard â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var toast = new bootstrap.Toast(document.getElementById('copyToast'), { delay: 2000 });
    window.copyCode = function(id, btn){
        navigator.clipboard.writeText(document.getElementById(id).innerText).then(function(){
            btn.classList.add('copied');
            btn.innerHTML = '<i class="bi bi-check2 me-1"></i>Copiado';
            toast.show();
            setTimeout(function(){ btn.classList.remove('copied'); btn.innerHTML = '<i class="bi bi-copy me-1"></i>Copiar'; }, 2200);
        });
    };

    // â”€â”€ Theme toggle â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var html    = document.documentElement;
    var toggle  = document.getElementById('themeToggle');
    var saved   = localStorage.getItem('theme');
    var init    = saved || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    function applyTheme(t){ html.setAttribute('data-bs-theme', t); localStorage.setItem('theme', t); }
    applyTheme(init);
    toggle.addEventListener('click', function(){ applyTheme(html.getAttribute('data-bs-theme') === 'dark' ? 'light' : 'dark'); });

    // â”€â”€ Smooth scroll â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    document.querySelectorAll('a[href^="#"]').forEach(function(a){
        a.addEventListener('click', function(e){
            var t = document.querySelector(a.getAttribute('href'));
            if(t){ e.preventDefault(); t.scrollIntoView({ behavior:'smooth', block:'start' }); }
        });
    });
})();

