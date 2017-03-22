window.Ama = (function (Ama) {

    function List (opts) {
        opts = opts || {};
        this.onDelete = opts.onDelete || function () {};
        this.onView = opts.onView || function () {};
    }

    List.prototype.view = function () {
        return {
            id      : 'ama-list',
            view    : 'datatable',
            columns : [
                {
                    id        : 'title',
                    header    : 'Title',
                    fillspace : 1,
                    template  : '<div class="title">#title#</div>'
                },
                {
                    id       : 'author',
                    header   : 'Author',
                    template : '<div class="author">#author#</div>'
                },
                {
                    id       : 'icon',
                    header   : '',
                    template : '<div class="icon">#icon#</div>'
                }
            ],
            on : {
                onBeforeLoad : function() {
                    this.showOverlay('Loading...');
                },
                onAfterLoad : function() {
                    this.hideOverlay();
                    if (!this.count()) {
                        this.showOverlay('There are no AMAs');
                    }
                }
            },
            onClick : {
                'icon' : this.onDelete.bind(this),
                'title' : this.onView.bind(this)
            }
        };
    };

    Ama.List = List;
    return Ama;
} (window.Ama || {}));
