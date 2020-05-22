<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>文章列表新增页面</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="名称">
			    	<el-input v-model="form.name"></el-input>
			  </el-form-item>
			  <el-form-item label="父">
			    	<el-input v-model="form.parent.name" readonly></el-input>
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
  	<script src="/js/api.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			form: {
        				name: '',
        				parent: {
        					id: '',
        					name: ''
        				}
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/cms/category/list';
      			},
      			add() {
      				axios.post('/cms/category/save', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      			let parentId = getQueryString('parent');
      			if (parentId != null) {
      				axios.get('/cms/category/get/' + parentId).then((response) => {
	      				this.form.parent = response.data;
	      			});
      			}
      		}
    	})
  	</script>
</html>