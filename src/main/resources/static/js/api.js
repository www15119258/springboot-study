function hasPerm(perm) {
	return axios.get('/security/hasPerm?perm=' + perm); 
}
Vue.prototype.$hasPerm = hasPerm;
