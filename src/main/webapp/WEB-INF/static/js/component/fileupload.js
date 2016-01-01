(function () {
    var global = 0;
    var FileComponent = window.FileComponent = function (container, loadedFiles) {
        container = $(container);
        var id = 'fileupload' + global++;

        container.html('<label class="btn btn-success" for="' + id + '"><input id="' + id + '" type="file" name="files[]" data-url="/uploadfile" style="display:none;" multiple> 添加文件 </label><div class="loading"></div><div class="file_list">');
        var trigger = container.find("#" + id);
        var list = container.find(".file_list");
        var loading = container.find(".loading").hide();
        //上传成功的File
        this.files = [];
        this.finish = 0;
        var self = this;

        var addFile = function(file) {
            var fileItem = $('<div>' + file.fileName + ' <a href="javascript:void(0);" title="删除">删除</a></div>');
            list.append(fileItem);
            this.files.push(file.fileId);
            this.finish--;
            fileItem.find("a").on('click', function () {
                fileItem.remove();
                var index = self.files.indexOf(file.fileId);
                self.files.splice(index, 1);
            });
        }.bind(this);

        if (loadedFiles) {
            this.finish = loadedFiles.length;
            loadedFiles.forEach(addFile);

        }



        trigger.fileupload({
            dataType: 'json',

            done: function (e, data) {
                var result = data.result;
                if (result.state == 'SUCCESS') {
                    addFile(result);
                } else {
                    alert(result.state);
                }
            },

            start: function (e) {
                loading.show();
                console.log('start', e);
                self.finish++;
            },

            progress: function (e, data) {


            },
            progressall: function (e, data) {
                //var progress = parseInt(data.loaded / data.total * 100, 10);
                loading.hide();
                //bar.css(
                //    'width',
                //    progress + '%'
                //);
            },
            dropZone: null,
            pasteZone: null
        });


    };

    FileComponent.prototype.getFiles = function () {
        return this.files;
    }
    FileComponent.prototype.isFinish = function () {
        return this.finish == 0;
    }

})();