var colors = d3.scaleOrdinal(d3.schemeCategory10);
//https://itw01.com/8RJUESI.html

var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height"),
	Id = +svg.attr("the_SVG_ID"),
    node,
    link;

svg.append('defs').append('marker')
    .attrs({'id':'arrowhead',
        'viewBox':'-0 -5 10 10',
        'refX':13,
        'refY':0,
        'orient':'auto',
        'markerWidth':10,
        'markerHeight':10,   //箭頭大小
        'xoverflow':'visible'})
    .append('svg:path')
    .attr('d', 'M 0,-5 L 10 ,0 L 0,5')
    .attr('fill', '#999')
    .style('stroke','none');

var simulation = d3.forceSimulation()  //創造一個新的simulation (這個模型包含著一個nodes的數組)  //看第94行
    .force("link", d3.forceLink().id(function (d) {return d.id}).strength(0.1)) //節點之間的距離
    .force("charge", d3.forceManyBody())
	
    .force("center", d3.forceCenter(width / 2, height / 2)) //起始位置
	.force('charge', d3.forceManyBody().strength(-250)); //引力設定，負數為排斥，正數為吸引，避免節點重疊
	//.force("center", d3.forceCenter(width / 3, height / 2));

function setup(links, nodes) {
    link = svg.selectAll(".link")
        .data(links)
        .enter()
        .append("line")
        .attr("class", "link")
        .attr('marker-end','url(#arrowhead)') // 根據箭頭標記的id號標記箭頭

    link.append("title")
        .text(function (d) {return d.type;});

    edgepaths = svg.selectAll(".edgepath")
        .data(links)
        .enter()// 新增資料到選擇集edgepath
        .append('path') // 生成折線
        .attrs({
            'class': 'edgepath',
            'fill-opacity': 0,
            'stroke-opacity': 0,
            'id': function (d, i) {return 'edgepath' + i}
        })
        .style("pointer-events", "none");

    edgelabels = svg.selectAll(".edgelabel")
        .data(links)
        .enter()
        .append('text') // 為每一條連線建立文字區域
        .style("pointer-events", "none")
		.style("fill", function (d, i) {
			if(d.type == "FRIENDS") return "#aaa";
			if(d.type == "Tracking") return "#66CDAA";
			if(d.type == "Following") return "orangered"})
        .attrs({
            'class': 'edgelabel',
            'id': function (d, i) {return 'edgelabel' + i},
            'font-size': 12  //關聯的字 'fill': '#aaa000'
            
        });

    edgelabels.append('textPath') // 設定文字內容
        .attr('xlink:href', function (d, i) {return '#edgepath' + i})
        .style("text-anchor", "middle")
        .style("pointer-events", "none")
        .attr("startOffset", "50%") //讓文字在link中間
        .text(function (d) {console.log(d.type); return d.type});

    node = svg.selectAll(".node")
        .data(nodes)
        .enter()
        .append("g")
        .attr("class", "node")
		.attr('text-anchor', 'middle') // 節點名稱放在圓圈中間位置
        .call(d3.drag()
                .on("start", dragstarted)
                .on("drag", dragged) // 拖拽過程
                .on("end", dragended)
        )
		;
		
	
	
    node.append("circle")
        .attr("r", 8) // node 大小
        .style("fill", function (d, i) {return colors(i)}) //節點顏色
		

    node.append("title") //滑鼠靠在節點上時談出的訊息
        .text(function (d) {return "[  " 
		+JSON.parse(d.total).social +"  ] : "
		+JSON.parse(d.total).name+",    "
		+JSON.parse(d.total).location+",    "
		+JSON.parse(d.total).email});
		
    var text=node.append("text")
	//JSON.parse  將Json字串 轉換成物件
    text.append("tspan").attr("x",0).attr("dy", "1.2em").text(function (d) {return JSON.parse(d.total).name})//節點上要顯示的文字
    //text.append("tspan").attr("x",0).attr("dy", "1.2em").attr("font-style","italic").text(function (d) {return d.label})

    simulation //把nodes array 放入
        .nodes(nodes)
        .on("tick", ticked);

    simulation.force("link")
        .links(links);
}

function ticked() { //監聽圖元素的位置變化
    link
        .attr("x1", function (d) {return d.source.x;})
        .attr("y1", function (d) {return d.source.y;})
        .attr("x2", function (d) {return d.target.x;})
        .attr("y2", function (d) {return d.target.y;});

    node
        .attr("transform", function (d) {return "translate(" + d.x + ", " + d.y + ")";})
		.attr("cx", function(d) {
            return d.x = Math.max(16, Math.min(width - 16, d.x));     //限制節點在svg的範圍內，不跑出svg視窗
            })
        .attr("cy", function(d) {
            return d.y = Math.max(16, Math.min(height - 16, d.y));
            });

	// 更新連線位置
    edgepaths.attr('d', function (d) {
        return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
    });

    edgelabels.attr('transform', function (d) {
        if (d.target.x < d.source.x) {
            var bbox = this.getBBox();

            rx = bbox.x + bbox.width / 2;
            ry = bbox.y + bbox.height / 2;
            return 'rotate(180 ' + rx + ' ' + ry + ')';
        }
        else {
            return 'rotate(0)';
        }
    });
}

function dragstarted(d) {
    if (!d3.event.active) 
		simulation.alphaTarget(0.2) // 設定衰減係數，對節點位置移動過程的模擬，數值越高移動越快，數值範圍[0，1]
		.restart() // 拖拽節點後，重新啟動模擬
    d.fx = d.x; // d.x是當前位置，d.fx是靜止時位置
    d.fy = d.y;
}

function dragged(d) {
    d.fx = d3.event.x; 
    d.fy = d3.event.y;
}

   function dragended(d) {
       if (!d3.event.active) simulation.alphaTarget(0);
       d.fx = undefined; // 解除dragged中固定的座標
       d.fy = undefined;
   }
