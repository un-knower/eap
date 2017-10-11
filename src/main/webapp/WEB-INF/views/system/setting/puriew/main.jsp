<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/system/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>puriew</title>
</head>
<body>
<div style="margin: 15px;">
    <div id="topLayout">
        <div  id="toolbar">
            <button id='addBtn' class="layui-btn layui-btn-small">
                <i class="layui-icon">&#xe608;</i> 添加
            </button>
        </div>
    </div>
    <table id='bootstrapTable'>
    </table>
</div>
</body>
<script type="text/javascript">
    layui.use([ 'layer', 'form' ], function(layer, form) {
        var layer = layui.layer;
        var form =  layui.form;
        $("#addBtn").click(function () {
            showModel("新增","${pageContext.request.contextPath}/resource/link?url=system\setting/form&puriewId=0");
        });

        //弹出录入框
        function showModel(title,url) {
            layer.open({
                id:"model",
                type:2,
                title:title,
                content:url,
                area:["550px","550px"],
                offset: '0px',
                shade:false,
                maxmin:true,
                success:function (layero, index) {

                }
            })
        };

        $('#bootstrapTable').bootstrapTable({
            url:"${pageContext.request.contextPath}/puriew/list",
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
            uniqueId : "puriewId", //每一行的唯一标识，一般为主键列
            singleSelect : true,//设置true禁止多选
             showToggle : false, //是否显示详细视图和列表视图的切换按钮
            cardView : false, //是否显示详细视图
            detailView : false, //是否显示父子表
            showHeader : true,//是否显示列头
            showFooter : false,//是否显示列脚
            contentType : "application/x-www-form-urlencoded", //解决POST提交问题
            columns : [{checkbox : true},
                {
                    title:"权限ID",
                    field:"puriewId",
                },
                {
                    title:"权限名称",
                    field:"name",
                },
                {
                    title:"序号",
                    field:"seq",
                },
                {
                    title:"权限表达式",
                    field:"expression",
                },
                {
                    title:"创建时间",
                    field:"createTime",
                },
                {
                    title:"修改时间",
                    field:"updateTime",
                },
                {
                    title:"是否有效",
                    field:"valid",
                },
                {
                    title:"创建人",
                    field:"creater",
                },
                {
                    title : "操作",
                    align : "center",
                    events : {
                        'click .edit' : function(e, value, row, index) {
                            $('#bootstrapTable').bootstrapTable('check',index);
                            showModel("编辑","${pageContext.request.contextPath}/resource/link?url=system\setting/form&puriewId="+row.puriewId);
                        },
                        'click .delete' : function(e, value, row, index) {
                            $('#bootstrapTable').bootstrapTable('check',index);
                            del(row.puriewId);
                        }
                    },
                    formatter : function () {
                        return ['<button type="button" class="edit layui-btn layui-btn-small">编辑</button>&nbsp;&nbsp;&nbsp;',
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
                return '输入关键字查询';
            }
        });
        function queryParams(params) {
            var param = {
                pageSize : params.limit, //页面大小
                pageNum : this.pageNumber, //页码
                searchText : params.search,
            }
            return param;
        }
    });

    function del(puriewId) {
        layer.confirm("删除数据不可恢复，请确认？",{
            btn: ['确定','取消'],
            offset: '150px',
        },function () {
            $.post('${pageContext.request.contextPath}/puriew/delete',
                    {puriewId : puriewId},
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
        },function () {

        });
    }

    function refreshTable() {
        $('#bootstrapTable').bootstrapTable('refresh');
    }
</script>
</html>