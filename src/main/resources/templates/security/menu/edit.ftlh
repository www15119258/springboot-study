<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>菜单编辑页面</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="名称">
			    	<el-input v-model="form.name"></el-input>
			  </el-form-item>
			  <el-form-item label="URL">
			    	<el-input v-model="form.url"></el-input>
			  </el-form-item>
			  <el-form-item label="父">
			    	<el-input v-model="form.parent.name" readonly></el-input>
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
        				name: '',
        				url: '',
        				parent: {
        					id: '',
        					name: ''
        				}
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/security/menu/list';
      			},
      			update() {
      				axios.put('/security/menu/update', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      			let paths = document.location.href.split('/');
      			let id = paths[paths.length - 1];
      			axios.get('/security/menu/get/' + id).then((response) => {
      				this.form = response.data;
      			});
      		}
    	})
  	</script>
</html>