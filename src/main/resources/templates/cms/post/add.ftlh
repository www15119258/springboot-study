<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>文章新增页面</title>
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
			  <el-form-item label="文章分类">
			  		<el-tree ref="tree" node-key="id" :data="categorys" :props="defaultProps" check-strictly show-checkbox></el-tree>
			  </el-form-item>
			  <el-form-item>
			    	<el-button type="primary" @click="addPost();">提交</el-button>
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
        			},
        			categorys: [],
        			defaultProps: {
        				children: 'children',
          				label: 'name'
        			}
        		}
      		},
      		methods: {
      			goback() {
      				window.location = '/cms/post/list';
      			},
      			addPost() {
      				let categoryIds = this.$refs['tree'].getCheckedKeys();
      				if (categoryIds.length > 0) {
      					this.form.categorys = categoryIds.map((id) => { return { id }; });
      				}
      				axios.post('/cms/post/save', this.form).then((response) => {
      					this.goback();
	      			});
      			}
      		},
      		mounted() {
      			axios.get('/cms/category/getTree').then((response) => {
      				this.categorys = response.data;
      			});
      		}
    	})
  	</script>
</html>