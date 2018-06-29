var neo = neo4j.v1;
var driver = neo.driver("bolt://localhost", neo.auth.basic("neo4j", "neo4j"));
var session = driver.session();
var session2 = driver.session();

var tx = session.beginTransaction();

//MATCH (FbUser:FbUser) RETURN FbUser.name AS name, FbUser.location AS location, FbUser.email AS email, FbUser.birthday AS birthday

var ans;


function f1(){
		var contact = new Object();
	var user=[]
	return tx.run("MATCH (FbUser:FbUser) RETURN FbUser.name AS name, FbUser.location AS location, FbUser.email AS email, FbUser.birthday AS birthday").then(function(result){
		
		result.records.forEach(function (rec){
			contact.name = rec._fields[0]; 
			contact.local = rec._fields[1];
			contact.email = rec._fields[2];
			contact.birthday = rec._fields[3];
			
			user.push(JSON.stringify(contact));
			console.log(contact.name);
		})
		f11()
		return user
    });	
	
	function f11(){
		var b = JSON.parse(String('{"bb" : ['+ user +']}'));
		var counts = b.bb.reduce((p, c) => {
		  var list_in =[];
		  list_in.push(String(c.name +" : Email("+ c.email +"), Location("+c.local +"), Birthday(" + c.birthday +")"));
		  console.log(list_in);
		  
		  var div_data_bind = d3.select("try").selectAll("div").data(list_in);
			div_set = div_data_bind.enter().append("div");
			div_data_bind.exit().remove();
			div_set.text(function(d,i) {
			   return d;
			});
			div_set.style("background", "#ffcc00");
			div_set.style("white-space", "nowrap");
			div_set.style("margin", "5px");
			div_set.style("height", "30px");
			div_set.style("padding-top", "5px");
			div_set.style("padding-left", "5px");
		}, {});
		
		
		var div_data_bind = d3.select("try").selectAll("div").data(user);
		div_set = div_data_bind.enter().append("div");
		div_data_bind.exit().remove();
		div_set.text(function(d,i) {
		   return d;
		});
		div_set.style("background", "#ffcc00");
		div_set.style("white-space", "nowrap");
		div_set.style("margin", "5px");
		div_set.style("height", "30px");
		div_set.style("padding-top", "5px");
		div_set.style("padding-left", "5px");
	}

	
	var colors = d3.scale.ordinal().range(["#AAA", "steelblue", "green", "orange", "brown"]);
	var hoverHtml = {'Introvert': '<h1>Introverts</h1>Like to be by themselves', 
		'Extrovert': '<h1>Extroverts</h1>Like the company of other people', 
		'Optimist': '<h1>Optimists</h1>Look on the bright side of life',
		'Neutral': '<h1>Neutrals</h1>Life could be good, it could be bad',
		'Pessimist': '<h1>Pessimists</h1>See the glass half empty'}
	var chordDiagram = d3.elts.flowChord().colors(colors).hoverHtml(hoverHtml).rimWidth(30);
	var data = [['Disposition','Optimist','Neutral','Pessimist'],
				['Introvert', 0.8, 0.4, 0.67], 
				['Extrovert', 0.2, 0.6, 0.33]]
	d3.select("h2o").datum(data).call(chordDiagram);

	}



function f2(){
	var contact = new Object();
	var user=[]
	return tx.run("MATCH (FbUser:FbUser) RETURN FbUser.name AS name, FbUser.location AS location, FbUser.email AS email, FbUser.birthday AS birthday").then(function(result){
		
		result.records.forEach(function (rec){
			contact.name = rec._fields[0]; 
			contact.local = rec._fields[1];
			contact.email = rec._fields[2];
			contact.birthday = rec._fields[3];
			
			user.push(JSON.stringify(contact));
			console.log(contact.name);
			
			//console.log(contact);
		})
		f11()
		return user
    });	
	
	function f11(){
		console.log(user);
		var b = JSON.parse(String('{"bb" : ['+ user +']}'));
		var counts = b.bb.reduce((p, c) => {
		  var local = c.local;
		  if (!p.hasOwnProperty(local)) {
			p[local] = 0;
		  }
		  p[local]++;
		  return p;
		}, {});
		var ans = Object.entries(counts);
		console.log(ans);
		console.log(b);
		
		var div_data_bind = d3.select("h2o").selectAll("div").data(ans);
		div_set = div_data_bind.enter().append("div");
		div_data_bind.exit().remove();
		div_set.text(function(d,i) {
		   return d[0]+' :'+d[1];
		});
		div_set.style("background", "#ffcc00");
		div_set.style("white-space", "nowrap");
		div_set.style("width", function(d,i) {
			return (d[1] / user.length*100*2)+"%";
		});
		/*div_set.style("background", function(d,i){
			if(d[0] === "Tainan") return "red";
			return "#ffcc00";
		  });*/
		div_set.style("margin", "5px");
		div_set.style("height", "20px");
		div_set.style("padding-left", "5px");
				

		//=====================================
		var w = 300,                        
			h = 200,                            
			r = 100,                            
			color = d3.scale.category20c(); 
			
		var vis = d3.select("cir")
        .append("svg:svg")             
        .data([ans])                  
            .attr("width", w)      
            .attr("height", h)
        .append("svg:g")          
            .attr("transform", "translate(" + r + "," + r + ")")	
		
		 var arc = d3.svg.arc()            
			.outerRadius(r);
		
		var pie = d3.layout.pie()          
			.value(function(d) { return d[1]; });

		var arcs = vis.selectAll("g.slice")  
        .data(pie)            
        .enter()  
            .append("svg:g")    
                .attr("class", "slice"); 

		arcs.append("svg:path")
                .attr("fill", function(d, i) { return color(i); } )
                .attr("d", arc);                                  
		arcs.append("svg:text")                                    
                .attr("transform", function(d) { 
                d.innerRadius = 0;
                d.outerRadius = r;
                return "translate(" + arc.centroid(d) + ")";       
            })
            .attr("text-anchor", "middle")
			.attr("style", "font-size:15px") 
            .text(function(d, i) { return ans[i][0]; });  

}

}


function f3(){
	var contact = new Object();
	var user=[]
	return tx.run("MATCH (FbUser:FbUser) RETURN FbUser.name AS name, FbUser.location AS location, FbUser.email AS email, FbUser.birthday AS birthday").then(function(result){
		
		result.records.forEach(function (rec){
			contact.name = rec._fields[0]; 
			contact.local = rec._fields[1];
			contact.email = rec._fields[2];
			contact.birthday = rec._fields[3];
			
			user.push(JSON.stringify(contact));
			//console.log(contact.name);
			
			//console.log(contact);
		})
		f11()
		return user
    });	
	
	function f11(){
		//console.log(user);
		var b = JSON.parse(String('{"bb" : ['+ user +']}'));
		var counts = b.bb.reduce((p, c) => {
		  var local = c.local;
		  if (!p.hasOwnProperty(local)) {
			p[local] = 0;
		  }
		  p[local]++;
		  return p;
		}, {});
		var ans = Object.entries(counts);
		//console.log(b);
		var rv = [];
		for(var i=0; i<ans.length; i++){ //去掉空值
		if(ans[i][0] != "" ) {
			rv.push(ans[i]);
			console.log(ans[i]);
		}
		}
		console.log(rv);
		a(d3);
		
		//=========================================================
			
		
		
		
}

}


