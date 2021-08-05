Date.prototype.format = function(format){ 
    var o =  { 
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(), //day 
    "h+" : this.getHours(), //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
    "S" : this.getMilliseconds() //millisecond 
    };
    if(/(y+)/.test(format)){ 
    	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
    for(var k in o)  { 
	    if(new RegExp("("+ k +")").test(format)){ 
	    	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	    } 
    } 
    return format; 
};

var TT = ReportForm = {
	
	// 格式化时间1
	formatDate : function(val,row){
		 if (val == undefined) {
		        return "";
		    }
		var now = new Date(val);
	    return now.format("yyyy/MM/dd");
	},
	
	// 格式化时间2
	formatDateTime : function(val,row){
		var now = new Date(val);
    	return now.format("yyyy-MM-dd hh:mm:ss");
	},
	// 格式化时间3
	formatDateTime3 : function(val,row){
		var now = new Date(val);
    	return now.format("yyyy-MM-dd");
	},
	
	// 格式化价格
	formatPrice : function(val,row){
		return ('￥'+val);
	},
	// 赠品的状态
	formatZPStatus : function formatStatus(val,row){
        if (val == 'true'){
            return '是';
        } else if(val == 'false'){
        	return '否';
        } else {
        	return '未知';
        }
    },
    formatYWLXStatus : function formatStatus(val,row){
        if (val == 'BZ'){
            return '标准应收';
        } else {
        	return '未知';
        }
    },
    // 单据状态
    formatDJStatus : function formatStatus(val,row){
        if (val == 'Z'){
            return '暂存';
        } else if(val == 'A'){
        	return '创建';
        } else if(val == 'B'){
        	return '审核中';
        }else if(val == 'C'){
        	return '已审核';
        }else if(val == 'D'){
        	return '重新审核';
        }else {
        	return '未知';
        }
    },
 // 排名
    formatPMStatus : function formatStatus(val,row){
        if (val == 1){
            return '第一名';
        } else if(val == 2){
        	return '第二名';
        } else if(val == 3){
        	return '第三名';
        }else if(val == 4){
        	return '第四名';
        }
    },
    formatppxxStatus : function formatStatus(val,row){
        if (val == 'y'){
            return '启用';
        } else if(val == 'n'){
        	return '停用';
        } 
    },
    
    formattbjdyStatus : function formatStatus(val,row){
        if (val == 'y' || val == '1'){
            return '已同步';
        } else if(val == 'n'|| val == '0'){
        	return '未同步';
        } 
    },
    
    formatcpxxStatus : function formatStatus(val,row){
        if (val == 'A'){
            return '否';
        } else if(val == 'B'){
        	return '是';
        } 
    },
    
    formatfbjdyStatus : function formatStatus(val,row){
        if (val == 'y'){
            return '已发布';
        } else if(val == 'n'){
        	return '未发布';
        } 
    },
    
    formatBom : function formatStatus(val,row){
        if (val == 'y'){
            return '需要';
        } else if(val == 'n'){
        	return '不需要';
        } 
    }
    
};

