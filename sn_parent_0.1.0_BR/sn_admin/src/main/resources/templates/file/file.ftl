<#import "/component/register.ftl" as  register/>

<div style="" class="">
    <section class="content-header">
        <h1>
            文件上传Demo<small></small>
        </h1>
    </section>
    <script type="text/javascript" src="${props('resourceUrl')}/js/upload.js"></script>
    <script type="text/javascript" src="${props('resourceUrl')}/js/jquery-form.js"></script>
    <script type="text/javascript">
        function successhand(obj){
            if(obj.code=='success'){
                layer.alert("上传成功:code = "+obj.code+"  message = "+obj.message + " file_path = "+ obj.othMsg.file_path);
            }else{
                layer.alert("上传失败");
            }
        }
    </script>
    <form id="uploadForm" action="/file/upLoad.dhtml" method="post" enctype="multipart/form-data">
        <input type="hidden"  name="business_type" value="010">
        <input type="hidden" name="business_no" value="00003344">
        <input type="hidden" name="file_desc" value="好">
        <table>
            <tr>
                <td>
                    <input type="file" name="upFile" id="upFile">
                </td>
            </tr>
        </table>
    </form>
    <input type="button" onclick="upLoad('uploadForm','successhand')" value="提交">
</div>