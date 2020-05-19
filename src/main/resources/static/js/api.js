function hasPerm(perm) {
	return axios.get('/security/hasPerm?perm=' + perm); 
}
Vue.prototype.$hasPerm = hasPerm;

function getQueryString(name) {  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return decodeURI(r[2]);
    return null;  
}