(function(){
    // â”€â”€ Scroll progress â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var prog = document.getElementById('scroll-progress');
    window.addEventListener('scroll', function(){
        var tot = document.documentElement.scrollHeight - window.innerHeight;
        prog.style.width = (tot > 0 ? (window.scrollY / tot * 100) : 0) + '%';
    }, {passive:true});

    // â”€â”€ Back to top â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var btt = document.getElementById('backTop');
    if (btt) { btt.addEventListener('click', function(){ window.scrollTo({top:0,behavior:'smooth'}); }); }
    window.addEventListener('scroll', function(){ btt.classList.toggle('show', window.scrollY > 300); }, {passive:true});

    // â”€â”€ Active nav â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var navLinks = document.querySelectorAll('#navbarDesktop .nav-link');
    var secs = ['overview','setup','db-embebida','datos','estructura-app','vistas','sdd','recursos'].map(function(id){ return document.getElementById(id); }).filter(Boolean);
    new IntersectionObserver(function(entries){
        entries.forEach(function(e){
            if(e.isIntersecting){
                navLinks.forEach(function(l){ l.classList.remove('active'); });
                var m = document.querySelector('#navbarDesktop a[href="#'+e.target.id+'"]');
                if(m) m.classList.add('active');
            }
        });
    }, { rootMargin: '-50% 0px -45% 0px' }).observe
    && secs.forEach(function(s){ new IntersectionObserver(function(entries){ entries.forEach(function(e){ if(e.isIntersecting){ navLinks.forEach(function(l){ l.classList.remove('active'); }); var m = document.querySelector('#navbarDesktop a[href="#'+e.target.id+'"]'); if(m) m.classList.add('active'); } }); }, { rootMargin: '-50% 0px -45% 0px' }).observe(s); });

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

    // â”€â”€ Animated counters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var counted = false;
    new IntersectionObserver(function(entries){
        if(entries[0].isIntersecting && !counted){
            counted = true;
            document.querySelectorAll('[data-count]').forEach(function(el){
                var t = +el.dataset.count, c = 0, step = Math.ceil(t/28);
                var tmr = setInterval(function(){ c = Math.min(c+step,t); el.textContent = c; if(c>=t) clearInterval(tmr); }, 45);
            });
        }
    }, { threshold: 0.5 }).observe(document.querySelector('.stats-bar') || document.body);

    // â”€â”€ Theme toggle â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var html    = document.documentElement;
    var toggle  = document.getElementById('themeToggle');
    var saved   = localStorage.getItem('theme');
    var init    = saved || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    function applyTheme(t){ html.setAttribute('data-bs-theme', t); localStorage.setItem('theme', t); }
    applyTheme(init);
    toggle.addEventListener('click', function(){ applyTheme(html.getAttribute('data-bs-theme') === 'dark' ? 'light' : 'dark'); });

    // â”€â”€ Health check â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    var dot = document.getElementById('health-dot');
    var lbl = document.getElementById('health-label');
    fetch('/actuator/health').then(function(r){ return r.json(); }).then(function(j){
        var up = j && j.status === 'UP';
        dot.className = up ? 'up' : 'down';
        lbl.textContent = up ? 'Servicio UP' : 'Servicio DOWN';
    }).catch(function(){ dot.className = 'down'; lbl.textContent = 'Sin respuesta'; });

    // â”€â”€ Smooth scroll â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    document.querySelectorAll('a[href^="#"]').forEach(function(a){
        a.addEventListener('click', function(e){
            var t = document.querySelector(a.getAttribute('href'));
            if(t){ e.preventDefault(); t.scrollIntoView({ behavior:'smooth', block:'start' }); }
        });
    });
})();


