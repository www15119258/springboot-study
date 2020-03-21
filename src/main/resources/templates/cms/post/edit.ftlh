<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>文章编辑页面</title>
</head>
<body>
	<div id="app">
		<el-form label-position="right" label-width="80px" :model="form">
			  <el-form-item label="标题">
			    	<el-input v-model="form.title"></el-input>
			  </el-form-item>
			  <el-form-item label="内容">
			    	<el-input type="textarea" v-model="form.content"></el-input>
			  </el-form-item>
			  <el-form-item label="摘要">
			    	<el-input type="textarea" v-model="form.summary"></el-input>
			  </el-form-item>
			  <el-form-item label="作者">
			    	<el-input v-model="form.author"></el-input>
			  </el-form-item>
			  <el-form-item>
			    	<el-button type="primary" @click="updatePost();">提交</el-button>
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
        				title: '',
        				content: '',
        				summary: '',
        				author: ''
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/cms/post/list';
      			},
      			updatePost() {
      				axios.put('/cms/post/update', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      			let paths = document.location.href.split('/');
      			let id = paths[paths.length - 1];
      			axios.get('/cms/post/get/' + id).then((response) => {
      				this.form = response.data;
      			});
      		}
    	})
  	</script>
</html>