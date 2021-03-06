(function () {
    var PageList = function (container, options) {
        this.parseData = options.parseData;
        this.url = options.url;
        this.lastColumn = options.lastColumn;
        this.queryData = options.queryData;

        this.table = $(container).find("table tbody");
        this.pageNav = $('<nav><ul class="pagination navbar-right"></ul></nav>').appendTo(container).find('ul');
        this.getList(1);
        var self = this;
        this.pageNav.on("click", "a", function (e) {
            var link = $(this);
            var page = link.data("page");
            if (link.parent().hasClass("disabled") || link.parent().hasClass("active")) {
                return;
            }
            self.getList(page);
        });
    };


    PageList.prototype.getList = function (page) {
        var self = this;
        if (page == this.currentPage) {
            return;
        }
        this.currentPage = page;
        $.ajax({
            url: this.url + "?page=" + page,
            data: self.queryData,
            dataType: "json",
            type: "post",
            success: function (res) {
                var data = self.parseData(res);
                self.table.empty();
                data.list.forEach(function (rowData) {
                    var row = $("<tr></tr>");
                    rowData.forEach(function (cell, i) {
                        var content = cell;
                        if (i == rowData.length - 1 && self.lastColumn) {
                            content = self.lastColumn.replace(/\{(\w+)\}/g, function (m, $1) {
                                return cell[$1];
                            });
                        }
                        row.append("<td>" + content + "</td>");

                        self.table.append(row);
                    });
                });
                self.renderNav(data.total, page);
            }
        })
    };

    PageList.prototype.renderNav = function (total, current) {
        //current 从1 开始

        this.pageNav.empty();
        if (total == 0) {
            return;
        }
        this.pageNav.append('<li class="' + (current == 1 ? "disabled" : "") + '"> <a href="javascript:void(0)" data-page="' + (current - 1) + '" aria-label="Previous"> <span aria-hidden="true">&laquo;</span> </a> </li>');
        if (total <= 5) {
            for (var i = 1; i <= total; i++) {
                this.pageNav.append('<li class="' + (current == i ? "active" : "") + '"><a href="javascript:void(0);" data-page="' + i + '">' + i + '</a></li>');
            }
        } else {
            if (current > 2) {
                this.pageNav.append('<li><a href="javascript:void(0);" data-page="1">1</a></li>');
                if (current > 3) {
                    this.pageNav.append('<li class="disabled"><a href="javascript:void(0);" data-page="1">...</a></li>');
                }
            }
            for (var i = current - 1; i < current + 2; i++) {
                if (i <= 0 || i > total) {
                    continue;
                }
                this.pageNav.append('<li class="' + (current == i ? "active" : "") + '"><a href="javascript:void(0);" data-page="' + i + '">' + i + '</a></li>');
            }
            if (current < total - 1) {
                if (current < total - 2) {
                    this.pageNav.append('<li class="disabled"><a href="javascript:void(0);" data-page="1">...</a></li>');
                }

                this.pageNav.append('<li><a href="javascript:void(0);" data-page="' + total + '">' + total + '</a></li>');

            }
        }
        this.pageNav.append('<li class="' + (current == total ? "disabled" : "") + '"> <a href="javascript:void(0)" data-page="' + (current + 1) + '" aria-label="Next"> <span aria-hidden="true">&raquo;</span> </a> </li>');
    };


    window.PageList = PageList;
})();
