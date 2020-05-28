<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>xxx的博客</title>
</head>
<body>
	<div id="app">
		<el-menu mode="horizontal" @select="selectCategory">
			<el-menu-item index="0">首页</el-menu-item>
			<template v-for="category in categorys">
				<el-menu-item :index="category.id + ''" v-if="category.children === null || category.children === undefined || category.children.length === 0">
					{{category.name}}
				</el-menu-item>
				<el-submenu :index="category.id + ''" v-else>
					<template slot="title">{{category.name}}</template>
					<template v-for="category2 in category.children">
						<el-menu-item :index="category2.id + ''" v-if="category2.children === null || category2.children === undefined || category2.children.length === 0">
							{{category2.name}}
						</el-menu-item>
						<el-submenu :index="category2.id + ''" v-else>
							<template slot="title">{{category2.name}}</template>
							<template v-for="category3 in category2.children">
								<el-menu-item :index="category3.id + ''">{{category3.name}}</el-menu-item>
							</template>
						</el-submenu>
					</template>
				</el-submenu>
			</template>
		</el-menu>
		<el-row :gutter="20">
		  <el-col :span="18">
		  	<div>
		  		<h1>{{post.title}}</h1>
		  		<h3>{{post.author}} {{post.publishDate}}
		  		 <a :href="'/category/' + category.id" v-for="category in post.categorys">{{category.name}}</a>
		  		</h3>
		  		<div v-html="post.content">
		  		</div>
		  	</div>
		  </el-col>
		  <el-col :span="6">
		  	<div>
		  		<h2>推荐文章</h2>
		  		<h3 v-for="post in posts">{{post.title}} ({{post.views}})</h3>
		  	</div>
		  </el-col>
		</el-row>
		<div style="text-align: center; padding: 20px 0; background-color: grey;">
			版权所有：xxxx
		</div>
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
        			categorys: [],
        			posts: [],
        			post: {}
        		}
      		},
      		methods: {
      			selectCategory(key, keyPath) {
      				if (key === '0') {
      					window.location = '/';
      				} else {
			        	window.location = '/category/' + key;
      				}
		      	},
      			queryPost() {
      				axios.get('/post/recommend/' + this.post.id).then((response) => {
	      				this.posts = response.data;
	      			});
      			}
      		},
      		mounted() {
      			let paths = document.location.href.split('/');
      			let id = paths[paths.length - 1];
      			axios.get('/post/get/' + id).then((response) => {
      				this.post = response.data;
      				if (this.post === '') {
      					window.location = '/';
      				} else {
      					this.queryPost();
      				}
      			});
      			axios.get('/getCategoryTree').then((response) => {
      				this.categorys = response.data;
      			});
      		}
    	})
  	</script>
</html>