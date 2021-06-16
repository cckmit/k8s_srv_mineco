/*! JointJS v0.9.2 - JavaScript diagramming library  2014-09-17 


This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
if (typeof exports === 'object') {

    var joint = {
        util: require('../src/core').util,
        shapes: {
            basic: require('./joint.shapes.basic')
        },
        dia: {
            Element: require('../src/joint.dia.element').Element,
            Link: require('../src/joint.dia.link').Link
        }
    };
}

joint.shapes.fsa = {};

joint.shapes.fsa.State = joint.dia.Element.extend({
    defaults: joint.util.deepSupplement({
        type: 'fsa.State',
        size: { width: 60, height: 60 }        
    }, joint.dia.Element.prototype.defaults)
}); 

/*
// ESTABLE -- ANTIGUO
joint.shapes.fsa.State = joint.shapes.basic.Circle.extend({
    defaults: joint.util.deepSupplement({
        type: 'fsa.State',
        attrs: {
            circle: { 
            	'stroke-width': 3,
            	fill: '#FFFF99'
            },
            text: { 'font-weight': 'bold' },
        }
    
    }, joint.shapes.basic.Circle.prototype.defaults)
});
*/

/*
joint.shapes.fsa.State = joint.shapes.basic.Circle.extend({});
*/

joint.shapes.fsa.StartState = joint.dia.Element.extend({

    //markup: '<g class="rotatable"><g class="scalable"><circle/></g></g>',
    //markup: '<circle fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" cx="68.58877" cy="72.87253" r="10.40976" id="svg_44"/>',
    defaults: joint.util.deepSupplement({

        type: 'fsa.StartState',
        size: { width: 20, height: 20 }
        /*
        ,

        attrs: {
            circle: {
                transform: 'translate(10, 10)',
                r: 10,
                fill: 'black'
            }
        }
        */

    }, joint.dia.Element.prototype.defaults)
});

joint.shapes.fsa.EndState = joint.dia.Element.extend({

    //markup: '<g class="rotatable"><g class="scalable"><circle class="outer"/><circle class="inner"/></g></g>',
    //markup: ['<circle fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" cx="68.98797" cy="66.0861" r="10.40976" id="svg_44"/>',
             //'<circle id="svg_46" r="13.4982" cy="66.26747" cx="69.16168" stroke="#000000" fill="none"/>'].join(''),
    defaults: joint.util.deepSupplement({

        type: 'fsa.EndState'
        /*
        ,
        size: { width: 20, height: 20 }
        ,
        attrs: {
            '.outer': {
                transform: 'translate(10, 10)',
                r: 10,
                fill: '#FFFFFF',
                stroke: 'black'
            },

            '.inner': {
                transform: 'translate(10, 10)',
                r: 6,
                fill: '#000000'
            }
        }
        */
    }, joint.dia.Element.prototype.defaults)
});

joint.shapes.fsa.Arrow = joint.dia.Link.extend({

    defaults: joint.util.deepSupplement({
	type: 'fsa.Arrow',
        attrs: { '.marker-target': { d: 'M 10 0 L 0 5 L 10 10 z' }},
        smooth: true
    }, joint.dia.Link.prototype.defaults)
});

if (typeof exports === 'object') {

    module.exports = joint.shapes.fsa;
}
