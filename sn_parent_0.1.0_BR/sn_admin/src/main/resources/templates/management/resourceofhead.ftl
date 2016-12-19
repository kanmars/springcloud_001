<#--
resource.ftl和resourceofmenu.ftl在内容上是一致的，区别在于
1、menu.ftl中引用的是resourceofmenu.ftl，内容必须存在
2、每一个独立页面例如sysdic.ftl中引用的是resource.ftl，其中仅当为嵌入模式时，resource.ftl中才会有内容
3、非iframe嵌入模式，使用jqueryLoad加载js时，会导致js加载两次，页面会发生很多js调用两次或以上的BUG
-->
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/font-awesome.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/AdminLTE.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/blue.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/daterangepicker-bs3.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/datepicker3.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrapValidator.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/ionicons.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/dataTables.bootstrap.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/_all-skins.min.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/page.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/new.css">
        <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrap-addtabs.css">
        <script>
            if(typeof(webUrl)=='undefined'){
                webUrl="";
            }
        </script>
        <!-- Bootstrap 3.3.5 -->
        <script src="${props('resourceUrl')}/js/plugins/bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="${props('resourceUrl')}/js/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="${props('resourceUrl')}/js/plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${props('resourceUrl')}/js/plugins/dist/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${props('resourceUrl')}/js/plugins/dist/demo.js"></script>
        <script src="${props('resourceUrl')}/js/plugins/iCheck/icheck.min.js"></script>
        <script src="${props('resourceUrl')}/js/pager_v1.0.2.js"></script>

        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/moment.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/daterangepicker.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap-datepicker.zh-CN.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap-datetimepicker.zh-CN.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap-addtabs.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/layer/layer.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap.autocomplete.js"></script>
        <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrapValidator.js"></script>
