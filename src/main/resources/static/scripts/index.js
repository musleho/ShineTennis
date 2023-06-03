window.addEventListener('load', () => {
    //Styling the navbar based on scroll position  
    function blendNavbar() {
        let navbar = $("nav");
        navbar.removeClass("bg-primary");
        navbar.addClass("bg-transparent");
        navbar.removeClass("shadow");
    }

    function showNavbar() {
        let navbar = $("nav");
        navbar.removeClass("bg-transparent");
        navbar.addClass("bg-primary");
        navbar.addClass("shadow");
    }

    function toggleNavbar() {
        if (window.scrollY > 0) {
            showNavbar();
        }
        else {
            blendNavbar();
        }
    }

    $(window).on('scroll', toggleNavbar);
});