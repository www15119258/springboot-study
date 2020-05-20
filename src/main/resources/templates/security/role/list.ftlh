<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>角色列表页面</title>
</head>
<body>
	<div id="app">
		<el-button type="primary" @click="query();">查询</el-button>
		<el-button type="primary" @click="add();" v-if="showAddBtn">新增角色</el-button>
    	<el-table :data="data">
	      	<el-table-column prop="rolename" label="名称">
	      	</el-table-column>
	      	<el-table-column prop="nickname" label="昵称">
	      	</el-table-column>
	      	<el-table-column prop="description" label="描述">
	      	</el-table-column>
	      	<el-table-column label="操作" width="250">
		      	<template slot-scope="scope">
		      		<el-button type="text" size="small" v-if="showAssignPermBtn"
		        		@click="assignPerms(scope.row)">分配权限</el-button>
		        	<el-button type="text" size="small" v-if="showAssignMenuBtn"
		        		@click="assignMenus(scope.row)">分配菜单</el-button>
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
	  	<el-dialog title="权限列表" :visible.sync="hasPermDialogVisible" :show-close="false">
	  		<el-button icon="el-icon-plus" type="primary" circle size="mini" @click="showAddPermPage();"
	  			style="position: absolute; right: 20px; top: 20px;"></el-button>
	  		<el-table :data="hasPermData">
		      	<el-table-column prop="name" label="名称">
		      	</el-table-column>
		      	<el-table-column prop="perm" label="权限字符">
		      	</el-table-column>
		      	<el-table-column prop="description" label="描述">
		      	</el-table-column>
		      	<el-table-column label="操作" width="100">
			      	<template slot-scope="scope">
			      		<el-button type="text" size="small" 
			        		@click="removePerm(scope.row)">移除</el-button>
			      	</template>
			    </el-table-column>
		    </el-table>
		  	<span slot="footer" class="dialog-footer">
		    	<el-button type="primary" @click="hasPermDialogVisible = false">关闭</el-button>
		  	</span>
		</el-dialog>
		<el-dialog title="权限列表" :visible.sync="notHasPermDialogVisible" :show-close="false">
	  		<el-table :data="notHasPermData">
		      	<el-table-column prop="name" label="名称">
		      	</el-table-column>
		      	<el-table-column prop="perm" label="权限字符">
		      	</el-table-column>
		      	<el-table-column prop="description" label="描述">
		      	</el-table-column>
		      	<el-table-column label="操作" width="100">
			      	<template slot-scope="scope">
			      		<el-button type="text" size="small" 
			        		@click="addPerm(scope.row)">增加</el-button>
			      	</template>
			    </el-table-column>
		    </el-table>
		    <div class="block">
		    	<el-pagination
	  				@size-change="notHasPermHandleSizeChange"
	  				@current-change="notHasPermHandleCurrentChange"
	  				:current-page="notHasPermCurrentPage"
	  				:page-sizes="[10, 20, 50]"
	  				:page-size="notHasPermPageSize"
	  				layout="total, sizes, prev, pager, next, jumper"
	  				:total="notHasPermTotal">
		    	</el-pagination>
		  	</div>
		  	<span slot="footer" class="dialog-footer">
		    	<el-button type="primary" @click="notHasPermDialogVisible = false">关闭</el-button>
		  	</span>
		</el-dialog>
		<el-dialog title="菜单列表" :visible.sync="menusDialogVisible" :show-close="false" @opened="menusDialogOpened">
	  		<el-tree ref="tree" node-key="id" :data="menus" :props="defaultProps" check-strictly show-checkbox></el-tree>
	  		<span slot="footer" class="dialog-footer">
			    <el-button @click="menusDialogVisible = false">取 消</el-button>
			    <el-button type="primary" @click="updateMenus">更 新</el-button>
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
        			hasPermDialogVisible: false,
        			hasPermData: [],
        			notHasPermDialogVisible: false,
        			notHasPermData: [],
        			currentRole: {},
        			notHasPermCurrentPage: 1,
        			notHasPermPageSize: 10,
        			notHasPermTotal: 0,
        			showAddBtn: false,
        			showAssignPermBtn: false,
        			showEditBtn: false,
        			showDeleteBtn: false,
        			showAssignMenuBtn: false,
        			menusDialogVisible: false,
        			menus: [],
        			defaultProps: {
        				children: 'children',
          				label: 'name'
        			}
        		}
      		},
      		methods: {
      			add() {
      				window.location = '/security/role/add';
      			},
      			edit(row) {
      				window.location = '/security/role/edit/' + row.id;
      			},
      			deleteRow(row) {
      				this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
			        }).then(() => {
			          	axios.delete('/security/role/delete/' + row.id).then(() => {
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
      				axios.get('/security/role/findPage/' + (this.currentPage - 1) + '/' + this.pageSize).then((response) => {
	      				this.data = response.data.content;
	      				this.total = response.data.totalElements;
	      			});
      			},
      			assignPerms(row) {
      				this.currentRole = row;
      				this.hasPermData = row.perms;
      				this.hasPermDialogVisible = true;
      			},
      			assignMenus(row) {
      				this.currentRole = row;
      				this.menusDialogVisible = true;
      			},
      			updateMenus() {
      				let data = new Object();
      				data.roleId = this.currentRole.id;
      				data.menuIds = this.$refs['tree'].getCheckedKeys();
      				axios.post('/security/role/updateMenus', Qs.stringify(data, { arrayFormat: 'repeat' })).then((response) => {
      					this.query();
	      				this.menusDialogVisible = false;
	      			});
      			},
      			menusDialogOpened() {
      				this.menus = [];
      				axios.get('/security/menu/getTree').then((response) => {
	      				this.menus = response.data;
	      				this.$refs['tree'].setCheckedKeys(this.currentRole.menus.map((m) => m.id));
	      			});
      			},
      			showAddPermPage() {
      				this.notHasPermCurrentPage = 1;
      				this.notHasPermPageSize = 10;
      				this.notHasPermTotal = 0;
      			    this.queryNotHasPerm();
      				this.notHasPermDialogVisible = true;
      			},
      			queryNotHasPerm() {
      				axios.get('/security/role/findRoleUnAssignPerms/' + this.currentRole.id + '/' + (this.notHasPermCurrentPage - 1) + '/' + this.notHasPermPageSize)
      				.then((response) => {
	      				this.notHasPermData = response.data.content;
	      				this.notHasPermTotal = response.data.totalElements;
	      				if(this.notHasPermTotal > 0 && this.notHasPermData.length === 0) {
	      					this.notHasPermCurrentPage = this.notHasPermCurrentPage - 1;
	      					this.queryNotHasPerm();
	      				}
	      			});
      			},
      			notHasPermHandleSizeChange(val) {
      				this.notHasPermPageSize = val;
      				this.queryNotHasPerm();
      			},
      			notHasPermHandleCurrentChange(val) {
      				this.notHasPermCurrentPage = val;
      				this.queryNotHasPerm();
      			},
      			removePerm(row) {
      				axios.post('/security/role/removePerm', Qs.stringify({ roleId: this.currentRole.id, permId: row.id })).then((response) => {
      					this.currentRole.perms = this.currentRole.perms.filter((r) => r.id !== row.id);
      					this.hasPermData = this.currentRole.perms;
	      			});
      			},
      			addPerm(row) {
      				axios.post('/security/role/assignPerm', Qs.stringify({ roleId: this.currentRole.id, permId: row.id })).then((response) => {
      					this.currentRole.perms.push(response.data);
      					this.queryNotHasPerm();
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      			this.$hasPerm('jbf:security:role:save').then((result) => { this.showAddBtn =  result.data });
      			this.$hasPerm('jbf:security:role:assignPerm').then((result) => { this.showAssignPermBtn =  result.data });
      			this.$hasPerm('jbf:security:role:assignMenu').then((result) => { this.showAssignMenuBtn =  result.data });
      			this.$hasPerm('jbf:security:role:edit').then((result) => { this.showEditBtn =  result.data });
      			this.$hasPerm('jbf:security:role:delete').then((result) => { this.showDeleteBtn =  result.data });
      		}
    	})
  	</script>
</html>