(function () {

    var FileComponent = window.FileComponent = function (container) {
        container = $(container);
        var trigger = container.find('.fileupload');
        var list = container.find(".file_list");
        var bar = container.find(".progress");

        //上传成功的File
        this.files = [];
        this.finish = false;

        var self = this;

        trigger.fileupload({
            dataType: 'json',

            done: function (e, data) {
                $.each(data.result, function (index, file) {
                    list.append('<div>' + file.fileName + ' ' + file.fileSize + ' ' + file.fileType + '</div>');
                });
                self.finish = true;
            },

            start: function (e) {
                bar.css('width', '0%');
                self.finish = false;
            },

            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                bar.css(
                    'width',
                        progress + '%'
                );
            }
        });


    };

    FileComponent.prototype.getFiles = function () {
        return this.files;
    }
    FileComponent.prototype.isFinish = function () {
        return this.finish;
    }

})();