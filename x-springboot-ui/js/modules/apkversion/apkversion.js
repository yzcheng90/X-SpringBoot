$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'apkversion/apkversion/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '更新内容', name: 'updateContent', index: 'update_content', width: 80 }, 			
			{ label: '版本码', name: 'versionCode', index: 'version_code', width: 80 }, 			
			{ label: '版本号', name: 'versionName', index: 'version_name', width: 80 }, 			
			{ label: '包名', name: 'packageName', index: 'package_name', width: 80 },
			{ label: '下载地址', name: 'downloadUrl', index: 'download_url', width: 80 },
			{ label: '文件名', name: 'appName', index: 'app_name', width: 80 }, 			
			{ label: 'MD5值', name: 'md5Value', index: 'md5_value', width: 80 }, 			
			{ label: '文件大小', name: 'fileSize', index: 'file_size', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '上传人', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '是否强制安装', name: 'isForce', index: 'is_force', width: 80 }, 			
			{ label: '是否可忽略该版本', name: 'isIgnorable', index: 'is_ignorable', width: 80 }, 			
			{ label: '是否静默下载', name: 'isSilent', index: 'is_silent', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    new AjaxUpload('#upload', {
        action: baseURL + 'sys/oss/upload/apk?token=' + token,
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if(vm.config.type == null){
                alert("云存储配置未配置");
                return false;
            }
            if (!(extension && /^(apk)$/.test(extension.toLowerCase()))){
                alert('只支持APK文件！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
            	console.log(r);
                vm.apkVersion = r.apkVersion;
                vm.add();
            }else{
                alert(r.msg);
            }
        }
    });

});

var vm = new Vue({
	el:'#app',
	data:{
		showList: true,
		title: null,
		apkVersion: {},
        config: {}
	},
    created: function(){
        this.getConfig();
    },
	methods: {
		query: function () {
			vm.reload();
		},
        getConfig: function () {
            $.getJSON(baseURL + "sys/oss/config", function(r){
                vm.config = r.config;
            });
        },
		add: function(){
			vm.showList = false;
			vm.title = "新增";
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.apkVersion.id == null ? "apkversion/apkversion/save" : "apkversion/apkversion/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.apkVersion),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "apkversion/apkversion/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "apkversion/apkversion/info/"+id, function(r){
                vm.apkVersion = r.apkVersion;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});