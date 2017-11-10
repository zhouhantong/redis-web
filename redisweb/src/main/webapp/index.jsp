<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="${ctx}/statck/easyui/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx}/statck/easyui/js/jquery.easyui.min.js"></script>
    <link href="${ctx}/statck/easyui/css/themes/default/easyui.css"
          rel="stylesheet" type="text/css"/>
    <link href="${ctx}/statck/easyui/css/themes/icon.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<table id="tt"></table>
<script type="text/javascript">
    $(function () {
        $("#tt").datagrid({
            url:"selectUserForPage.do",
            pagination:true,
            columns:[[
                {title:"编号",field:"userId",sortable:true},
                {title:"姓名",field:"userName",sortable:true},
                {title:"手机",field:"userPhone",sortable:true},
                {title:"年龄",field:"userAge",sortable:true},
                {title:"性别",field:"userSex",formatter:function (value) {
                    if (value==1)
                        return "男";
                    else
                        return "女";
                }},
                {title:"状态",field:"userStatus",formatter:function (value) {
                    if(value==1)
                        return "有效";
                    else
                        return "无效";
                }},
                {title:"薪资",field:"userSal",sortable:true},
                {title:"edit?",field:"edit",sortable:true},
                {title:"UUID",field:"uuid",sortable:true}
            ]],
            toolbar:'#tools'
        });
    })
</script>
<div id="tools">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addUser()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeUser()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editUser()"></a>
    <%--<a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="editUser()"></a>--%>
</div>
<div id="addWin" class="easyui-window" data-options="width:400,height:400,title:'添加用户',closed:true">
    <form method="post" id="addForm">
        <table width="350" height="350" align="center">
            <tr>
                <td>姓名：</td>
                <td><input type="text" name="userName" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="userPassword" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>手机：</td>
                <td><input type="text" name="userPhone" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>年龄：</td>
                <td><input type="text" name="userAge" class="easyui-numberbox"/></td>
            </tr>
            <tr>
                <td>性别：</td>
                <td><input type="radio" name="userSex" value="1"/>男
                    <input type="radio" name="userSex" value="2"/>女
                </td>
            </tr>
            <tr>
                <td>状态：</td>
                <td><input type="text" name="userStatus" class="easyui-combobox"
                           data-options="data:[{value:'0',text:'禁用'},{value:'1',text:'激活'}]"/></td>
            </tr>
            <tr>
                <td>薪资：</td>
                <td><input type="text" name="userSal" class="easyui-numberbox"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="button" id="addButton" value="添加" class="dialog-button"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="editWin" class="easyui-window" data-options="width:400,height:400,title:'修改用户',closed:true">
    <form method="post" id="editForm">
        <table width="350" height="350" align="center">
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="userName" id="userName"/></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="userPassword" id="userPassword"/></td>
            </tr>
            <tr>
                <td>手机：</td>
                <td><input type="text" name="userPhone" id="userPhone"/></td>
            </tr>
            <tr>
                <td>年龄：</td>
                <td><input type="text" name="userAge" class="easyui-numberbox" id="userAge"/></td>
            </tr>
            <tr>
                <td>性别：</td>
                <td><input type="radio" name="userSex" value="1" id="userSex" />男
                    <input type="radio" name="userSex" value="2" id="userSex1"/>女
                </td>
            </tr>
            <tr>
                <td>状态：</td>
                <td><input type="text" name="userStatus" class="easyui-combobox"
                           data-options="data:[{value:'0',text:'禁用'},{value:'1',text:'激活'}]" id="userStatus"/></td>
            </tr>
            <tr>
                <td>薪资：</td>
                <td><input type="text" name="userSal" class="easyui-numberbox" id="userSal"/></td>
            </tr>
            <input type="hidden" name="userId" id="userId"/>
            <input type="hidden" name="uuid" id="uuid"/>
            <tr>
                <td colspan="2" align="center"><input type="button" id="editButton" value="修改"/></td>
            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    function addUser() {
        $("#addWin").window("open");
    }
    function editUser() {
        var arrays=$("#tt").datagrid("getSelections");
        if (arrays.length==1){
            var user=arrays[0];
            $("#userName").val(user.userName);
            $("#userPassword").val(user.userPassword);
            $("#userPhone").val(user.userPhone);
            $("#userAge").numberbox("setValue",user.userAge);
            $("#userSex").val(user.userSex);
            if($("#userSex").val()==1){
                $("#userSex").attr("checked",true);
            }else {
                $("#userSex1").attr("checked",true);
            }
            $("#userStatus").combobox("setValue",user.userStatus);
            $("#userSal").numberbox("setValue",user.userSal);
            $("#userId").val(user.userId);
            $("#uuid").val(user.uuid);
            $("#editWin").window("open");
        }else if(arrays.length==0){
            $.messager.confirm("警告","请选择一条数据进行修改！")
        }else {
            $.messager.confirm("警告","不能选择多条数据！");
        }
    }
    function removeUser() {
        var array=$("#tt").datagrid("getSelections");
        if (array.length>0){
            var ids="";
            var names="";
            for(var i=0;i<array.length;i++){
                names+=array[i].userName+",";
                if(array[i].userId!=null){
                    ids+=array[i].userId+",";
                }else {
                    ids+=array[i].uuid+",";
                }
            }
            $.messager.confirm("警告","确认要删除"+names+"这"+array.length+"个员工吗？",function (result) {
                if(result){
                    $.post("deleteUser.do",
                        {ids:ids},function (data) {
                            if(data){
                                $.messager.alert("结果","删除成功！");
                                $("#tt").datagrid("reload");
                            }else {
                                $.messager.alert("结果","删除失败！");
                            }
                        }
                    )
                }
            })
        }else {
            $.messager.confirm("警告","请至少选择一条数据！");
        }
    }
    $(function () {
        $("#addButton").click(function () {
            $("#addForm").form("submit",{
                url:"insertUser.do",
                success:function (data) {
                    if(data){
                        $.messager.alert("结果","添加成功！");
                        $("#addWin").window("close");
                        $("#tt").datagrid("reload");
                    }
                }
            })
        })
        $("#editButton").click(function () {
            $("#editForm").form("submit",{
                url:"updateUser.do",
                success:function (data) {
                    if (data){
                        $.messager.alert("结果","修改成功！");
                        $("#editWin").window("close");
                        $("#tt").datagrid("reload");
                    }
                }
            })
        })
    })

</script>
</body>
</html>
