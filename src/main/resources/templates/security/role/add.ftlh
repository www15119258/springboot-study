<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>角色新增页面</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="名称">
			    	<el-input v-model="form.rolename"></el-input>
			  </el-form-item>
			  <el-form-item label="昵称">
			    	<el-input v-model="form.nickname"></el-input>
			  </el-form-item>
			  <el-form-item label="描述">
			    	<el-input type="textarea" v-model="form.description"></el-input>
			  </el-form-item>
			  <el-form-item>
			    	<el-button type="primary" @click="add();">提交</el-button>
			    	<el-button @click="goback();">返回</el-button>
			  </el-form-item>
		</el-form>
  	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<!-- import JavaScript -->
  	<script src="/js/index.js"></script>
  	<script src="/js/axios.min.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			form: {
        				rolename: '',
        				nickname: '',
        				description: ''
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/security/role/list';
      			},
      			add() {
      				axios.post('/security/role/save', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      		}
    	})
  	</script>
</html>