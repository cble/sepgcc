(function () {
    var global = 0;
    var FileComponent = window.FileComponent = function (container) {
        container = $(container);
        var id = 'fileupload' + global++;

        container.html('<label class="btn btn-primary" for="' + id + '"><input id="' + id + '" type="file" name="files[]" data-url="/uploadFile" style="display:none;" multiple> 添加文件 </label><div class="file_list"></div>');
        var trigger = container.find("#" + id);
        var list = container.find(".file_list");

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
                console.log(e);
            },
            processstart:function(e){
                console.log(arguments)

            },
            progress: function (e, data) {
                var file = data.files[data.index];
                console.log(file)
//                var progress = parseInt(data.loaded / data.total * 100, 10);
//                list.append('<div>' + file.name + ' ' + file.size + ' ' + file.type + '<div class="process"></div></div>');
//                bar.css(
//                    'width',
//                        progress + '%'
//                );
            },
            progressall: function (e, data) {

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