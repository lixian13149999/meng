$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);

        var submenu = this.el.find(".submenu a");
        submenu.on('click',{el:submenu},this.checksub);

    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    Accordion.prototype.checksub = function (e) {
        var $el = e.data.el;
        $el.each(function () {
            $item = $(this);
            $item.removeClass('active');
        });

        $this = $(this);
        $this.addClass('active');
    }

    var accordion = new Accordion($('#sidebar_accordion'), false);
});

