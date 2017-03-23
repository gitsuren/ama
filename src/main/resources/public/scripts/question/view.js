window.Question = (function (Question) {
    var removeIcon = '<span class="fa-trash-o webix_icon right"></span>';
    var upvoteIcon = '<span class="fa fa-arrow-circle-o-up webix_icon"></span>';
    var downvoteIcon = '<span class="fa fa-arrow-circle-o-down webix_icon"></span>';
    var answerButton = '<input class="webixtype_form webix_el_button right ans_bttn"'+
    	'type="button" value="Answer Question"></input>';

    function View (opts) {
        opts = opts || {};
        this.ama = opts.ama;
        this.onDelete = opts.onDelete || function () {};
        webix.ui( new window.Ama.Dialog({
            id : 'win-create-answer',
            title: 'New Answer',
            body : new window.Ama.createForm('create-answer-form','Answer',
            		this.createAnswer.bind(this))
        }).view());
    }
    
    View.prototype.createAnswer= function (){
    	var params, el = $$('create-answer-form');

        if (el.validate()) {
            el.getTopParentView().hide();
            params = el.getValues();
            webix.ajax().post('/ama/' + this.ama.id + '/question/'+params.id+'/answer',params);

        } else {
            webix.message({
                type: 'error',
                text: 'Body cannot be empty'
            });
        }
    };
    
    View.prototype.repr = function (obj) {
        // TODO add check if user created question
        var template=  'Created Date: ' + obj.created +
            removeIcon + '<br/><span class="question">' + obj.body +'</span>';
            if(obj.answer){
            	template +='<span class="answer"><br/><p>'+obj.answer.body+'</p></span>';
            }else{
            	template += answerButton;
            }
            template += '<br/>' + upvoteIcon + downvoteIcon + '<br/> Upvotes: ' + obj.upVotes;
            template += '<br/><p> Downvotes: ' + obj.downVotes + '</p>';
            return template;
    };

    View.prototype.view = function () {
        return {
            view : 'dataview',
            id : 'questions',
            template : this.repr.bind(this),
            type : {
                height : 200,
                width : 'auto'
            },
            xCount : 1,
            yCount : 10,
            onClick : {
                'fa-trash-o' : this.onDelete.bind(this),
                'fa-arrow-circle-o-up' : (function(event,id) {
                	webix.ajax().post('/ama/' + this.ama.id + '/question/' + id + '/upvote')
                	.then(function(result) {
                		var question = result.json();
                		$$('questions').data.updateItem(question.id, question).refresh();
                	}); 
                }).bind(this),
                'fa-arrow-circle-o-down' : (function(event,id) {
                	webix.ajax().post('/ama/' + this.ama.id + '/question/' + id + '/downvote')
                	.then(function(result) {
                		var question = result.json();
                		$$('questions').data.updateItem(question.id, question).refresh();
                	});  
                }).bind(this),
                'ans_bttn' 	 : function(event,id) {
                	window.Ama.showDialog('win-create-answer');
                	$$('create-answer-form').setValues({'id':id});
                }
            }
        };
    };
    
    Question.View = View;
    return Question;
} (window.Question || {}));
