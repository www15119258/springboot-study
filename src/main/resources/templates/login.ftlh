<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>登录</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="用户名">
			    	<el-input v-model="form.username"></el-input>
			  </el-form-item>
			  <el-form-item label="密码">
			    	<el-input type="password" v-model="form.password"></el-input>
			  </el-form-item>
			  <el-form-item>
			    	<el-button type="primary" @click="login();">登录</el-button>
			  </el-form-item>
		</el-form>
  	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<script src="/js/index.js"></script>
  	<script src="/js/axios.min.js"></script>
  	<script src="/js/qs.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			form: {
        				username: '',
        				password: ''
        			}
        		}
      		},
      		methods: {
      			login() {
      				axios.post('/login/process', Qs.stringify(this.form)).then((response) => {
      					if (response.data.success) {
      						window.location = "/sys/index";
      					} else {
      						this.$message({
				            	type: 'error',
				            	message: response.data.message
				          	});  
      					}
	      			});
      			}
      		}
    	})
  	</script>
</html>