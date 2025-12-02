function drawGraph() {
    const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');

    const width = canvas.width;
    const height = canvas.height;
    const unit = Math.floor(height / 11);
    const microunit = unit / 10;
    const centerX = width / 2;
    const centerY = height / 2;

    ctx.clearRect(0, 0, width, height);

    let span = document.getElementById("coordsForm:valueR");
    let text = (span.value || span.innerText || "").trim();
    let R = parseFloat(text) * unit;
    if (isNaN(R) || R <= 0) { R = unit * 3.5; }

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + R / 2, centerY);
    ctx.lineTo(centerX, centerY + R / 2);
    ctx.lineTo(centerX, centerY + R);
    ctx.lineTo(centerX - R / 2, centerY + R);
    ctx.lineTo(centerX - R / 2, centerY);
    ctx.lineTo(centerX, centerY);
    ctx.arc(centerX, centerY, R / 2, 3 * Math.PI / 2, 0, false);

    ctx.closePath();
    ctx.fillStyle = 'rgba(51, 155, 215, 1)';
    ctx.fill();



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

    for (const p of points) {
        drawPoint(p, centerX, centerY, unit, ctx);
    }
}

function drawPoint(point, centerX, centerY, unit, ctx) {


    let graphx = centerX + (point.x * unit);
    let graphy = centerY - (point.y * unit);
    ctx.beginPath();
    ctx.arc(graphx, graphy, 3, 0, 2 * Math.PI);
    ctx.fillStyle = (point.hit) ? 'green' : 'red';
    ctx.fill();
    ctx.closePath();
}

function Loadpoints() {
    var hidden = document.getElementById('coordsForm:pointsJson');
    if (!hidden) {
        points = [];
        return;
    }

    var json = hidden.textContent || hidden.innerText;
    if (!json) {
        points = []
        return;
    }

    try {
        var loaded = JSON.parse(json);
        if (Array.isArray(loaded) && loaded.length > 0) {
            points = loaded;
        } else { points = []; }
    } catch (e) {
        console.error("Не смог распарсить JSON", e, json);
        points = [];
    }
}

function structureOk(s) {
    if (!/^[-\d.,]*$/.test(s)) return false;

    const minusCount = (s.match(/-/g) || []).length;
    if (minusCount > 1) return false;
    if (minusCount === 1 && s[0] !== '-') return false;

    const sepCount = (s.match(/[.,]/g) || []).length;
    if (sepCount > 1) return false;

    return true;
}

let points = [];

let R = 3.5;

Loadpoints();
drawGraph();

document.getElementById('graph').addEventListener('click', (event) => {
    const canvas = document.getElementById('graph');
    const unit = Math.floor(canvas.height / 11);

    const rect = canvas.getBoundingClientRect();
    let x = document.getElementById("FormForJS:FieldX");
    let y = document.getElementById("FormForJS:FieldY");
    let r = document.getElementById("FormForJS:FieldR");
    x.value = (event.clientX - rect.left - 200) / unit;
    y.value = -(event.clientY - rect.top - 200) / unit;
    r.value = R;
    document.getElementById("FormForJS:submitBtn").click();
});

const valueY = document.getElementById('coordsForm:valueY');
const msg = document.getElementById('msg');
const userBtn = document.getElementById('coordsForm:userBtn');

valueY.addEventListener('beforeinput', (e) => {
    if (e.inputType.startsWith('delete')) return;

    const insert = e.data ?? '';
    const { selectionStart: a, selectionEnd: b, value } = valueY;
    const next = value.slice(0, a) + insert + value.slice(b);
    if (!structureOk(next)) e.preventDefault();
});

valueY.addEventListener('input', () => {
    const txt = valueY.value.trim();
    let y = Number.parseFloat(txt.replace(',', '.'));

    if (y < -3 || y > 5) {
        userBtn.disabled = true;
        msg.textContent = "Y должен быть в диапазоне {-3, 5}";
    } else {
        userBtn.disabled = false;
        msg.textContent = "";
    }
});

const valueR = document.getElementById('coordsForm:valueR');
const msgr = document.getElementById('msgR');

valueR.addEventListener('beforeinput', (e) => {
    if (e.inputType.startsWith('delete')) return;

    const insert = e.data ?? '';
    const { selectionStart: a, selectionEnd: b, value } = valueR;
    const next = value.slice(0, a) + insert + value.slice(b);
    if (!structureOk(next)) e.preventDefault();
});

valueR.addEventListener('input', () => {
    const txt = valueR.value.trim();
    let r = Number.parseFloat(txt.replace(',', '.'));

    if ((r < 2 || r > 5) || isNaN(r)) {
        userBtn.disabled = true;
        msgr.textContent = "R должен быть в диапазоне {2, 5}";
    } else {
        R = r;
        drawGraph();
        userBtn.disabled = false;
        msgr.textContent = "";
    }
});