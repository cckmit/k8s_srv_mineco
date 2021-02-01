//CKEDITOR.plugins.add('strinsert',
//		{
//			requires : ['richcombo'],
//			init : function( editor )
//			{
//				//  array of strings to choose from that'll be inserted into the editor
//				var strings = [];
//				strings.push(['@@FAQ::displayList()@@', 'FAQs', 'FAQs']);
//				strings.push(['@@Glossary::displayList()@@', 'Glossary', 'Glossary']);
//				strings.push(['@@CareerCourse::displayList()@@', 'Career Courses', 'Career Courses']);
//				strings.push(['@@CareerProfile::displayList()@@', 'Career Profiles', 'Career Profiles']);
//
//				// add the menu to the editor
//				editor.ui.addRichCombo('strinsert',
//				{
//					label: 		'Insert Content',
//					title: 		'Insert Content',
//					voiceLabel: 'Insert Content',
//					className: 	'cke_format',
//					multiSelect:false,
//					panel:
//					{
//						css: [ editor.config.contentsCss, CKEDITOR.skin.getPath('editor') ],
//						voiceLabel: editor.lang.panelVoiceLabel
//					},
//
//					init: function()
//					{
//						this.startGroup( "Insert Content" );
//						for (var i in strings)
//						{
//							this.add(strings[i][0], strings[i][1], strings[i][2]);
//						}
//					},
//
//					onClick: function( value )
//					{
//						editor.focus();
//						editor.fire( 'saveSnapshot' );
//						editor.insertHtml(value);
//						editor.fire( 'saveSnapshot' );
//					}
//				});
//			}
//		});

CKEDITOR.editorConfig = function(config) {
    config.resize_enabled = false;
    config.entities = false;
    config.basicEntities = false;
    config.toolbar = 'MyToolbar';
    config.toolbar_MyToolbar = [
        ['Cut','Copy','Paste','PasteFromWord','SpellChecker','-','Undo','Redo','-','Find','Replace','-','RemoveFormat','SelectAll','Maximize'],
        ['Bold','Italic','Underline','Strike'],['Subscript','Superscript'],['Source'],
	    ['NumberedList','BulletedList','-','Outdent','Indent'],
	    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'], ['TextColor','BGColor'], ['Styles','Format','Font','FontSize'],
	    ['Image','Table','HorizontalRule','SpecialChar']];
//    config.extraPlugins = 'strinsert';
//    config.strinsert_strings = [
//                                {'value': '*|FIRSTNAME|*', 'name': 'First name'},
//                                {'value': '*|LASTNAME|*', 'name': 'Last name'},
//                                {'value': '*|INVITEURL|*', 'name': 'Activore invite URL'},
//                            ];
//                            config.strinsert_button_label = 'Tokens';
//                            config.strinsert_button_title = config.strinsert_button_voice = 'Insert token';
};
