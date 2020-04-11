<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>用户编辑页面</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="用户名">
			    	<el-input v-model="form.username"></el-input>
			  </el-form-item>
			  <el-form-item label="昵称">
			    	<el-input v-model="form.nickname"></el-input>
			  </el-form-item>
			  <el-form-item>
			    	<el-button type="primary" @click="update();">提交</el-button>
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
        				username: '',
        				nickname: ''
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/security/user/list';
      			},
      			update() {
      				axios.put('/security/user/update', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      			let paths = document.location.href.split('/');
      			let id = paths[paths.length - 1];
      			axios.get('/security/user/get/' + id).then((response) => {
      				this.form = response.data;
      			});
      		}
    	})
  	</script>
</html>