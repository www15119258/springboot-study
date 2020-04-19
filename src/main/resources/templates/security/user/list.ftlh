<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>用户列表页面</title>
</head>
<body>
	<div id="app">
		<el-button type="primary" @click="query();">查询</el-button>
		<el-button type="primary" @click="add();" v-if="showAddBtn">新增用户</el-button>
    	<el-table :data="data">
	      	<el-table-column prop="username" label="名称">
	      	</el-table-column>
	      	<el-table-column prop="nickname" label="昵称">
	      	</el-table-column>
	      	<el-table-column label="操作" width="200">
		      	<template slot-scope="scope">
		      		<el-button type="text" size="small" v-if="showAssignRoleBtn"
		        		@click="assignRoles(scope.row)">分配角色</el-button>
		        	<el-button type="text" size="small" v-if="showEditBtn"
		        		@click="edit(scope.row)">编辑</el-button>
	        		<el-button type="text" size="small" v-if="showDeleteBtn"
		        		@click="deleteRow(scope.row)">删除</el-button>
		      	</template>
		    </el-table-column>
	    </el-table>
	    <div class="block">
	    	<el-pagination
  				@size-change="handleSizeChange"
  				@current-change="handleCurrentChange"
  				:current-page="currentPage"
  				:page-sizes="[10, 20, 50]"
  				:page-size="pageSize"
  				layout="total, sizes, prev, pager, next, jumper"
  				:total="total">
	    	</el-pagination>
	  	</div>
	  	<el-dialog title="角色列表" :visible.sync="hasRoleDialogVisible" :show-close="false">
	  		<el-button icon="el-icon-plus" type="primary" circle size="mini" @click="showAddRolePage();"
	  			style="position: absolute; right: 20px; top: 20px;"></el-button>
	  		<el-table :data="hasRoleData">
		      	<el-table-column prop="rolename" label="名称">
		      	</el-table-column>
		      	<el-table-column prop="nickname" label="昵称">
		      	</el-table-column>
		      	<el-table-column label="操作" width="100">
			      	<template slot-scope="scope">
			      		<el-button type="text" size="small" 
			        		@click="removeRole(scope.row)">移除</el-button>
			      	</template>
			    </el-table-column>
		    </el-table>
		  	<span slot="footer" class="dialog-footer">
		    	<el-button type="primary" @click="hasRoleDialogVisible = false">关闭</el-button>
		  	</span>
		</el-dialog>
		<el-dialog title="角色列表" :visible.sync="notHasRoleDialogVisible" :show-close="false">
	  		<el-table :data="notHasRoleData">
		      	<el-table-column prop="rolename" label="名称">
		      	</el-table-column>
		      	<el-table-column prop="nickname" label="昵称">
		      	</el-table-column>
		      	<el-table-column label="操作" width="100">
			      	<template slot-scope="scope">
			      		<el-button type="text" size="small" 
			        		@click="addRole(scope.row)">增加</el-button>
			      	</template>
			    </el-table-column>
		    </el-table>
		    <div class="block">
		    	<el-pagination
	  				@size-change="notHasRoleHandleSizeChange"
	  				@current-change="notHasRoleHandleCurrentChange"
	  				:current-page="notHasRoleCurrentPage"
	  				:page-sizes="[10, 20, 50]"
	  				:page-size="notHasRolePageSize"
	  				layout="total, sizes, prev, pager, next, jumper"
	  				:total="notHasRoleTotal">
		    	</el-pagination>
		  	</div>
		  	<span slot="footer" class="dialog-footer">
		    	<el-button type="primary" @click="notHasRoleDialogVisible = false">关闭</el-button>
		  	</span>
		</el-dialog>
  	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<!-- import JavaScript -->
  	<script src="/js/index.js"></script>
  	<script src="/js/axios.min.js"></script>
  	<script src="/js/qs.js"></script>
  	<script src="/js/api.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			data: [],
        			currentPage: 1,
        			pageSize: 10,
        			total: 0,
        			hasRoleDialogVisible: false,
        			hasRoleData: [],
        			notHasRoleDialogVisible: false,
        			notHasRoleData: [],
        			currentUser: {},
        			notHasRoleCurrentPage: 1,
        			notHasRolePageSize: 10,
        			notHasRoleTotal: 0,
        			showAddBtn: false,
        			showAssignRoleBtn: false,
        			showEditBtn: false,
        			showDeleteBtn: false
        		}
      		},
      		methods: {
      			add() {
      				window.location = '/security/user/add';
      			},
      			edit(row) {
      				window.location = '/security/user/edit/' + row.id;
      			},
      			deleteRow(row) {
      				this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
			        }).then(() => {
			          	axios.delete('/security/user/delete/' + row.id).then(() => {
			          		window.location.reload();
			          	});
			        }).catch(() => {
			          	this.$message({
			            	type: 'info',
			            	message: '已取消删除'
			          	});          
			        });
      			},
      			handleSizeChange(val) {
      				this.pageSize = val;
      				this.query();
      			},
      			handleCurrentChange(val) {
      				this.currentPage = val;
      				this.query();
      			},
      			query() {
      				axios.get('/security/user/findPage/' + (this.currentPage - 1) + '/' + this.pageSize).then((response) => {
	      				this.data = response.data.content;
	      				this.total = response.data.totalElements;
	      			});
      			},
      			assignRoles(row) {
      				this.currentUser = row;
      				this.hasRoleData = row.roles;
      				this.hasRoleDialogVisible = true;
      			},
      			showAddRolePage() {
      				this.notHasRoleCurrentPage = 1;
      				this.notHasRolePageSize = 2;
      				this.notHasRoleTotal = 0;
      			    this.queryNotHasRole();
      				this.notHasRoleDialogVisible = true;
      			},
      			queryNotHasRole() {
      				axios.get('/security/user/findUserUnAssignRoles/' + this.currentUser.id + '/' + (this.notHasRoleCurrentPage - 1) + '/' + this.notHasRolePageSize)
      				.then((response) => {
	      				this.notHasRoleData = response.data.content;
	      				this.notHasRoleTotal = response.data.totalElements;
	      				if(this.notHasRoleTotal > 0 && this.notHasRoleData.length === 0) {
	      					this.notHasRoleCurrentPage = this.notHasRoleCurrentPage - 1;
	      					this.queryNotHasRole();
	      				}
	      			});
      			},
      			notHasRoleHandleSizeChange(val) {
      				this.notHasRolePageSize = val;
      				this.queryNotHasRole();
      			},
      			notHasRoleHandleCurrentChange(val) {
      				this.notHasRoleCurrentPage = val;
      				this.queryNotHasRole();
      			},
      			removeRole(row) {
      				axios.post('/security/user/removeRole', Qs.stringify({ userId: this.currentUser.id, roleId: row.id })).then((response) => {
      					this.currentUser.roles = this.currentUser.roles.filter((r) => r.id !== row.id);
      					this.hasRoleData = this.currentUser.roles;
	      			});
      			},
      			addRole(row) {
      				axios.post('/security/user/assignRole', Qs.stringify({ userId: this.currentUser.id, roleId: row.id })).then((response) => {
      					this.currentUser.roles.push(response.data);
      					this.queryNotHasRole();
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      			this.$hasPerm('jbf:security:user:save').then((result) => { this.showAddBtn =  result.data });
      			this.$hasPerm('jbf:security:user:assignRole').then((result) => { this.showAssignRoleBtn =  result.data });
      			this.$hasPerm('jbf:security:user:edit').then((result) => { this.showEditBtn =  result.data });
      			this.$hasPerm('jbf:security:user:delete').then((result) => { this.showDeleteBtn =  result.data });
      		}
    	})
  	</script>
</html>