$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/gen/list',
        datatype: "json",
        colModel: [			
			{ label: '表名', name: 'tableName', width: 100, key: true },
			{ label: 'Engine', name: 'engine', width: 70},
			{ label: '表备注', name: 'tableComment', width: 100 },
			{ label: '创建时间', name: 'createTime', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
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
});

var vm = new Vue({
	el:'#app',
	data:{
		q:{
			tableName: null
		},
        showList: true,
        config:{
            genTable:"",
            mainPath:"com.suke.czx",
            packagePath:"com.suke.czx.modules",
            moduleName:"",
            author:"czx",
            email:"object_czx@163.com"
        }
	},
	methods: {
		query: function () {
            vm.showList = true;
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'tableName': vm.q.tableName},
                page:1 
            }).trigger("reloadGrid");
		},
        showConfig:function(){
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            vm.config.genTable = tableNames;
            vm.showList = false;
        },
		generator: function() {
            if(vm.validator()){
                return ;
            }
			var data = JSON.stringify(vm.config);
			location.href = baseURL + "sys/gen/code?data=" + encodeURI(data);
		},
        validator: function () {
            if(isBlank(vm.config.mainPath)){
                alert("启动类所在包名不能为空");
                return true;
            }

            if(isBlank(vm.config.packagePath)){
                alert("包名不能为空");
                return true;
            }
            if(isBlank(vm.config.moduleName)){
                alert("模块名不能为空");
                return true;
            }
        }
	}
});

