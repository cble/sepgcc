(function () {
    var global = 0;
    var FileComponent = window.FileComponent = function (container) {
        container = $(container);
        var id = 'fileupload' + global++;

        container.html('<label class="btn btn-primary" for="' + id + '"><input id="' + id + '" type="file" name="files[]" data-url="/uploadFile" style="display:none;" multiple> 添加文件 </label><div class="progress"><div class="progress-bar" style="width: 0%;"></div></div><div class="file_list">');
        var trigger = container.find("#" + id);
        var list = container.find(".file_list");
        var bar = container.find(".progress").hide().find(".progress-bar");
        //上传成功的File
        this.files = [];
        this.finish = 0;

        var self = this;

        trigger.fileupload({
            dataType: 'json',

            done: function (e, data) {
                $.each(data.result, function (index, file) {
                    list.append('<div>' + file.fileName + ' ' + file.fileType + '</div>');
                    self.files.push(file.fileId);
                    self.finish--;
                });

            },

            start: function (e) {
                bar.css('width', '0').parent().show();
                console.log('start', e);
                self.finish++;
            },

            progress: function (e, data) {


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
        return this.finish == 0;
    }

})();