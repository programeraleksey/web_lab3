function drawTimelineGraph() {
    let graphs = document.getElementById("form:timelineJson");
    if (!graphs) return;

    var json = graphs.textContent || graphs.innerText;
    if (!json) return;

    try {
        var loaded = JSON.parse(json);
        if (!(Array.isArray(loaded) && loaded.length > 0)) {
            return;
        }
    } catch (e) {
        console.error("Не смог распарсить JSON", e, json);
    }

    for (const graphData of loaded) {
        console.log('canvas_' + graphData.id, graphData);
        let canvas = document.getElementById('canvas_' + graphData.id);
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const unit = Math.floor(height / 11);
        const microunit = unit / 10;
        const centerX = width / 2;
        const centerY = height / 2;
        const r = graphData.r * unit;

        ctx.beginPath();
        drawQuarter(1, -1, ctx, graphData.graph.firstQuarterFigure, r, centerX, centerY);
        drawQuarter(-1, -1, ctx, graphData.graph.secondQuarterFigure, r, centerX, centerY);
        drawQuarter(-1, 1, ctx, graphData.graph.thirdQuarterFigure, r, centerX, centerY);
        drawQuarter(1, 1, ctx, graphData.graph.fourthQuarterFigure, r, centerX, centerY);
        ctx.closePath();
        ctx.fillStyle = 'rgba(51, 155, 215, 1)';
        ctx.fill();
        drawCoords(ctx, centerX, centerY, width, height, unit, microunit);
    }
}

function drawQuarter(xsign, ysign, ctx, figure, r, centerX, centerY) {
    if (!figure) { return; }

    ctx.moveTo(centerX, centerY);
    switch (figure.type) {
        case 'triangle': {
            ctx.lineTo(centerX + xsign * figure.x * r, centerY);
            ctx.lineTo(centerX, centerY + ysign * figure.y * r);
            ctx.lineTo(centerX, centerY);
            break;
        }
        case 'rectangle': {
            ctx.lineTo(centerX + xsign * figure.x * r, centerY);
            ctx.lineTo(centerX + xsign * figure.x * r, centerY + ysign * figure.y * r);
            ctx.lineTo(centerX, centerY + ysign * figure.y * r);
            ctx.lineTo(centerX, centerY);
            break;
        }
        case 'circle': {
            const radius = r * figure.x;
            if (xsign > 0 && ysign < 0) {       // 1 четверть
                ctx.lineTo(centerX, centerY - radius);
                ctx.arc(centerX, centerY, radius, 3 / 2 * Math.PI, 2 * Math.PI, false);
            } else if (xsign < 0 && ysign < 0) { // 2 четверть
                ctx.lineTo(centerX - radius, centerY);
                ctx.arc(centerX, centerY, radius, Math.PI, 3 / 2 * Math.PI, false);
            } else if (xsign < 0 && ysign > 0) { // 3 четверть
                ctx.lineTo(centerX, centerY + radius);
                ctx.arc(centerX, centerY, radius, Math.PI / 2, Math.PI, false);
            } else {                            // 4 четверть
                ctx.lineTo(centerX + radius, centerY);
                ctx.arc(centerX, centerY, radius, 0, Math.PI / 2, false);
            }
            ctx.lineTo(centerX, centerY);
            break;
        }
        default:
            console.error("Unknown figure type:", figure.type);
            return;
    }

}

function drawCoords(ctx, centerX, centerY, width, height, unit, microunit) {
    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.strokeStyle = "black";
    ctx.stroke();

    ctx.font = "s12px monospace";
    ctx.strokeText("0", centerX + microunit, centerY - microunit);
    ctx.strokeText("1", centerX + unit - microunit, centerY - microunit);
    ctx.strokeText("2", centerX + unit * 2 - microunit, centerY - microunit);
    ctx.strokeText("3", centerX + unit * 3 - microunit, centerY - microunit);
    ctx.strokeText("4", centerX + unit * 4 - microunit, centerY - microunit);
    ctx.strokeText("5", centerX + unit * 5 - microunit, centerY - microunit);


    ctx.strokeText("-1", centerX - unit - microunit * 3, centerY - microunit);
    ctx.strokeText("-2", centerX - unit * 2 - microunit * 3, centerY - microunit);
    ctx.strokeText("-3", centerX - unit * 3 - microunit * 3, centerY - microunit);
    ctx.strokeText("-4", centerX - unit * 4 - microunit * 3, centerY - microunit);
    ctx.strokeText("-5", centerX - unit * 5 - microunit * 3, centerY - microunit);

    ctx.strokeText("1", centerX + microunit, centerY - unit + microunit);
    ctx.strokeText("2", centerX + microunit, centerY - unit * 2 + microunit);
    ctx.strokeText("3", centerX + microunit, centerY - unit * 3 + microunit);
    ctx.strokeText("4", centerX + microunit, centerY - unit * 4 + microunit);
    ctx.strokeText("5", centerX + microunit, centerY - unit * 5 + microunit);

    ctx.strokeText("-1", centerX + microunit, centerY + unit + microunit);
    ctx.strokeText("-2", centerX + microunit, centerY + unit * 2 + microunit);
    ctx.strokeText("-3", centerX + microunit, centerY + unit * 3 + microunit);
    ctx.strokeText("-4", centerX + microunit, centerY + unit * 4 + microunit);
    ctx.strokeText("-5", centerX + microunit, centerY + unit * 5 + microunit);
    ctx.closePath();
}

setTimeout(() => {
    drawTimelineGraph();
}, 500);