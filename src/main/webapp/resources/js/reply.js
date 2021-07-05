/**
 * 댓글 스크립트 - 자바스크립트 모듈(관련있는 함수들의 모임) 구성
 */
var replyService=(function(){
	//private
	function add(reply,callback){
		console.log("add method 호출");
		
		$.ajax({
			type:'post',
			url:'/replies/new',
			contentType:'application/json',
			data:JSON.stringify(reply),
			success:function(result){
				if(callback){
					callback(result);
				}
			}			
		})			
	}// add 종료
	
	function getList(param,callback){
		
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON({
			url:'/replies/pages/'+bno+'/'+page,
			success:function(data){
				if(callback){
					callback(data.replyCnt,data.list);
				}
			}
		})
	}//getList 종료
	
	
	function remove(rno,callback){
		
		$.ajax({
			url:'/replies/'+rno,  // /replies/3
			type:'delete',
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		})		
	}//remove 종료
	
	
	function update(reply,callback){
		$.ajax({
			url:'/replies/'+reply.rno,
			type:'put',
			data:JSON.stringify(reply),
			contentType:'application/json',
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		})
	}//update 종료
	
	function get(rno,callback){
		
		$.getJSON({
			url:'/replies/'+rno,
			success:function(data){
				if(callback){
					callback(data);
				}
			}
		})
	}
	
	function displayTime(timeValue){
		var today = new Date();
		
		var gap = today.getTime() - timeValue;
		var dateObj = new Date(timeValue);		
		
		if(gap < (1000*60*60*24)){ //댓글 단 날짜가 오늘이면 시분초
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [(hh > 9 ? '':'0')+hh, ':', (mi > 9 ? '':'0')+mi, ':', (ss > 9 ? '':'0')+ss].join('');
		}else{ //오늘이 아닌 경우 년/월/일
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			return [yy,'/',(mm > 9 ? '':'0')+mm,'/',(dd > 9 ?'':'0')+dd].join('');
		}
	}
	
	//public
	return {
		add:add,
		getList:getList,
		remove:remove,
		update:update,
		get:get,
		displayTime:displayTime
	}
})();



















