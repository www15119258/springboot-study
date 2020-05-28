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
		  	<h2>当前分类：{{category.name}}</h2>
		  	<div v-for="post in posts">
		  		<h1>{{post.title}}</h1>
		  		<h3>{{post.author}} {{post.publishDate}}
		  		 <a :href="'/category/' + category.id" v-for="category in post.categorys">{{category.name}}</a>
		  		</h3>
		  		<div v-html="post.summary">
		  		</div>
		  	</div>
		  	<div class="block">
		    	<el-pagination
	  				@size-change="handleSizeChange"
	  				@current-change="handleCurrentChange"
	  				:current-page="currentPage"
	  				:page-size="pageSize"
	  				layout="total, prev, pager, next"
	  				:total="total">
		    	</el-pagination>
		  	</div>
		  </el-col>
		  <el-col :span="6">
		  	<div>
		  		<h2>热门文章</h2>
		  		<h3 v-for="post in topPosts">{{post.title}} ({{post.views}})</h3>
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
        			currentPage: 1,
        			pageSize: 10,
        			total: 0,
        			topPosts: [],
        			category: {}
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
		      	handleSizeChange(val) {
      				this.pageSize = val;
      				this.queryPost();
      			},
      			handleCurrentChange(val) {
      				this.currentPage = val;
      				this.queryPost();
      			},
      			queryPost() {
      				axios.get('/post/findPageByCategory/' + this.category.id + '/' + (this.currentPage - 1) + '/' + this.pageSize).then((response) => {
	      				this.posts = response.data.content;
	      				this.total = response.data.totalElements;
	      			});
      			}
      		},
      		mounted() {
      			let paths = document.location.href.split('/');
      			let id = paths[paths.length - 1];
      			axios.get('/category/get/' + id).then((response) => {
      				this.category = response.data;
      				if (this.category === '') {
      					window.location = '/';
      				} else {
      					this.queryPost();
		      			axios.get('/category/hots/' + this.category.id).then((response) => {
		      				this.topPosts = response.data;
		      			});
      				}
      			});
      			axios.get('/getCategoryTree').then((response) => {
      				this.categorys = response.data;
      			});
      		}
    	})
  	</script>
</html>