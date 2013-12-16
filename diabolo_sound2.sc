s.boot;

// Communication OF / SuperCollider
// Déclaration des adresses des différents serveurs OSC
(
//a = NetAddr.new("127.0.0.1", 12000); // adresse du Serveur OSC de Processing
//b = NetAddr.new("127.0.0.1", 12001); // adresse du Serveur OSC de Pure Data
//c = NetAddr.new("127.0.0.1", 12002); // adresse du Serveur OSC de SuperCollider
n = NetAddr.new("192.168.0.17", 38291); // adresse du Serveur OSC de OF
)


// Envoi de message vers les trois serveurs
/*(
r = 80.rand();
a.sendMsg("/OSC_comm/test", r, 5.846, "SC_OSC_Message");
b.sendMsg("/OSC_comm/test", r, 5.846, "SC_OSC_Message");
c.sendMsg("/OSC_comm/test", r, 5.846, "SC_OSC_Message");
)*/

OSCFunc.trace(true);
OSCFunc.trace(false);


// Le Seveur OSC de SuperCollider (totalement illisible)
(
o = OSCFunc({ arg msg, time, addr, recvPort;
	if(msg[0]=='/hello',{
		//[msg, time, addr, recvPort].postln;
		msg[1].postln;
		msg[2].postln;
		msg[3].postln;
		'Match OK'.postln;
		x.set(\t_gate, 1, \modHz, msg[2]);
	});
	}, '/hello', n, 12001);
)


// Attention à bien libérer le serveur OSC quand on a fini
o.free;

//Definition de synthes

(
SynthDef.new(\sineTest, {
arg t_gate=1, modHz=1 ;
var freq, amp = 0.5, sig, env;

freq = SinOsc.kr(modHz).exprange(200,1000);

env = EnvGen.kr(Env.new(
		[0, 1, 0.2, 0],
		[0.1, 0.5, 0.5],
		[3, -3, 0]), t_gate);

sig = SinOsc.ar(freq) * amp * env;
Out.ar(0, sig);
}).add;
)

//x = Synth.new(\sineTest, [\t_gate, 1, \modHz, 32]);
x = Synth.new(\sineTest);
x.set(\t_gate, 1, \modHz, 12000);
x.set(\t_gate, 1);
x.free;

s.plotTree;