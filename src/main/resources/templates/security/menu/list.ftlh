<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>菜单列表页面</title>
</head>
<body>
	<div id="app">
		<el-button type="primary" @click="query();">刷新</el-button>
		<el-button type="primary" @click="add();" v-if="showAddBtn">新增菜单</el-button>
		<el-button type="primary" @click="edit();" v-if="showEditBtn">编辑菜单</el-button>
		<el-button type="primary" @click="deleteRow();" v-if="showDeleteBtn">删除菜单</el-button>
    	<el-tree ref="tree" node-key="id" :expand-on-click-node="false" :data="data" :props="defaultProps"></el-tree>
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
        			defaultProps: {
        				children: 'children',
          				label: 'name'
        			},
        			showAddBtn: false,
        			showEditBtn: false,
        			showDeleteBtn: false
        		}
      		},
      		methods: {
      			add() {
      				let parent = this.$refs['tree'].getCurrentKey();
      				if (parent != null) {
      					window.location = '/security/menu/add?parent=' + parent;
      				} else {
      					window.location = '/security/menu/add';
      				}
      			},
      			edit() {
      				let id = this.$refs['tree'].getCurrentKey();
      				if (id == null) {
      					this.$message({
			            	type: 'error',
			            	message: '请选择要编辑的节点'
			          	});  
      				} else {
      					window.location = '/security/menu/edit/' + id;
      				}
      			},
      			deleteRow() {
      				let id = this.$refs['tree'].getCurrentKey();
      				if (id == null) {
      					this.$message({
			            	type: 'error',
			            	message: '请选择要删除的节点'
			          	});  
      				} else {
      					this.$confirm('此操作将永久删除该记录及其子节点, 是否继续?', '提示', {
				          	confirmButtonText: '确定',
				          	cancelButtonText: '取消',
				          	type: 'warning'
				        }).then(() => {
				          	axios.delete('/security/menu/delete/' + id).then(() => {
				          		window.location.reload();
				          	});
				        }).catch(() => {
				          	this.$message({
				            	type: 'info',
				            	message: '已取消删除'
				          	});          
				        });
      				}
      			},
      			query() {
      				axios.get('/security/menu/getTree').then((response) => {
	      				this.data = response.data;
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      			this.$hasPerm('jbf:security:menu:save').then((result) => { this.showAddBtn =  result.data });
      			this.$hasPerm('jbf:security:menu:edit').then((result) => { this.showEditBtn =  result.data });
      			this.$hasPerm('jbf:security:menu:delete').then((result) => { this.showDeleteBtn =  result.data });
      		}
    	})
  	</script>
</html>