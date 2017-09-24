<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/system/common/base.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
		.clickEffect {
			cursor: pointer;
		}
	</style>
	<title>资源管理</title>
</head>
<body>
<div style="margin: 15px;">
	<div class="span6">
		<ul class="breadcrumb"></ul>
	</div>
	<div id="toolbar">
		<button id='addBtn' class="layui-btn layui-btn-small">
			<i class="layui-icon">&#xe608;</i> 添加菜单
		</button>
	</div>
	<table id='bootstrapTable'>
	</table>
</div>
</body>

<script type="text/javascript">
    var currentId = 0;
    layui.use([ 'layer', 'form' ], function(layer, form) {
        var layer = layui.layer;
        var form =  layui.form;

        $("#addBtn").click(function () {
            showModel("新增资源","${pageContext.request.contextPath}/resource/link?url=system/setting/resource/add&resourceId=0");
        });

        //弹出录入框
        function showModel(title,url) {
            layer.open({
                id:"model",
                type:2,
                title:title,
                content:url,
                area:["450px","550px"],
                offset: '0px',
                shade:false,
                maxmin:true,
                success:function (layero, index) {

                }
            })
        };
        $('#bootstrapTable').bootstrapTable({
            url:"${pageContext.request.contextPath}/resource/list",
            method:'GET',
            toolbar:"#toolbar",
            striped : true, //是否显示行间隔色
            cache : false, //是否使用缓存
            pagination : true, //是否显示分页（*）
            queryParams : queryParams,//传递参数（*）
            queryParamsType : 'limit',
            sidePagination : 'server', //分页方式
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 15, //每页的记录行数（*）
            pageList : [ 10, 15, 20, 50 ], //可供选择的每页的行数（*）
            search : true,//是否显示表格搜索
            searchAlign : "right",//指定搜索框位置
            dataField:"list",
            totalField:"total",
            searchOnEnterKey : true,
            strictSearch : true,
            showRefresh : true, //是否显示刷新按钮
            showColumns : false, //是否显内容列下拉框
            showPaginationSwitch : false,//是否显示 数据条数选择框
            minimumCountColumns : 2, //最少允许的列数
            clickToSelect : true, //是否启用点击选中行
            uniqueId : "resourceId", //每一行的唯一标识，一般为主键列
            singleSelect : true,//设置true禁止多选
            showToggle : false, //是否显示详细视图和列表视图的切换按钮
            cardView : false, //是否显示详细视图
            detailView : false, //是否显示父子表
            showHeader : true,//是否显示列头
            showFooter : false,//是否显示列脚
            contentType : "application/x-www-form-urlencoded", //解决POST提交问题
            columns : [{checkbox : true},
                {
                    title : "序号",
                    field : "seq",
                },{
                    title : "资源名称",
                    field : "name"
                },{
                    title : "资源类型",
                    field : "typeDesc"
                },{
                    title : "资源图标",
                    field : "icon",
                    formatter:function (value, row, index) {
                        var ulHtml='';
                        if(row.icon.indexOf('fa-') !== -1) {
                            ulHtml += '<i class="fa ' + value + ' fa-2x" aria-hidden="true" data-icon="' + value + '"></i>';
                        } else {
                            ulHtml += '<i class="layui-icon" data-icon="' + value + '">' + value + '</i>';
                        }
                        return ulHtml;
                    },
                    align:"center"
                },{
                    title : "链接",
                    field : "link"
                },{
                    title : "备注",
                    field : "puriewName"
                } , {
                    title : "操作",
                    align : "center",
                    events : {
                        'click .enter': function (e, value, row, index) {
                            currentId = row.resourceId;
                            refreshTable();
                            loadPath();
                        },
                        'click .edit' : function(e, value, row, index) {
                            $('#bootstrapTable').bootstrapTable('check',index);
                            showModel("新增资源","${pageContext.request.contextPath}/resource/link?url=system/setting/resource/add&resourceId="+row.resourceId);
                        },
                        'click .delete' : function(e, value, row, index) {
                            $('#bootstrapTable').bootstrapTable('check',index);
                            del(row.resourceId);
                        }
                    },
                    formatter : function () {
                        return [ '<button type="button" class="enter layui-btn layui-btn-small">进入</button>&nbsp;&nbsp;&nbsp;',
                            '<button type="button" class="edit layui-btn layui-btn-small">编辑</button>&nbsp;&nbsp;&nbsp;',
                            '<button type="button" class="delete layui-btn layui-btn-small">删除</button>&nbsp;&nbsp;&nbsp;',].join('');
                    }
                }],
            onLoadError : function(status) { //加载失败时执行
                $.messager.show({
                    title : 'Error',
                    msg : "数据加载失败"
                });
            },
            formatSearch: function () {
                return '输入权限名称或权限表达式查询';
            }
        });

        function queryParams(params) {
            var param = {
                pageSize : params.limit, //页面大小
                pageNum : this.pageNumber, //页码
                searchText : params.search,
                parentId:currentId
            }
            return param;
        }

        loadPath();
    });

    function del(resourceId) {
        $.post('${pageContext.request.contextPath}/resource/delete',
            {
                resourceId : resourceId
            },
            function (data, status) {
                if (status == "success") {
                    if (data.body.resultCode == "0") {
                        refreshTable();
                    }else {
                        layer.msg(data.body.resultContent);
                    }
                }else {
                    layer.msg("网络错误");
                }
            }).error(function (e) {
            layer.msg("网络错误："+e.status);
        })
    }

    function loadPath() {
        $.get('${pageContext.request.contextPath}/resource/getPath/',
            {
                resourceId : currentId
            }, function(data, status) {
                if (status == "success") {
                    if (data.body.resultCode == "0") {
                        var result = data.body.entity;
                        var $breadcrumb = $(".breadcrumb");
                        $breadcrumb.empty();
                        var html = "";
                        for (var i = 0; i < result.length; i++) {
                            $breadcrumb.append("<li><a class='clickEffect' name='"+result[i].resourceId+"'>"
                                + result[i].name
                                + "</a></li>");
                            $("a[name=" + result[i].resourceId + "]").bind("click", {
                                index : i
                            }, clickHandler);
                        }
                        function clickHandler(event) {
                            var i = event.data.index;
                            currentId = result[i].resourceId;
                            refreshTable();
                            loadPath();
                        }
                    }
                }
            });
    }

    function refreshTable() {
        $('#bootstrapTable').bootstrapTable('refresh');
    }
</script>
</html>