s.boot;

(
SynthDef(\snare, {|amp= 10, attack= 0.01, release= 0.3, filter= 600, pan= 0|
	var snd, env, efx;
	env= EnvGen.kr(Env.perc(attack, release), doneAction: 2);
	snd= PinkNoise.ar(env*amp);
	efx= BPF.ar(snd, filter, 0.5, 3);
	Out.ar(0, Pan2.ar(efx, pan));
}).add;
)

(
x = {
	arg amp= 10, attack= 0.001, release= 0.1, filter= 1600, pan= 0, t_gate = 1;
	var snd, env, efx;
	env= EnvGen.kr(Env.perc(attack, release), t_gate);
	snd= PinkNoise.ar(env*amp);
	efx= BPF.ar(snd, filter, 0.5, 3);
	Out.ar(0, Pan2.ar(efx, pan));
}.play;
)

x.set(\t_gate, 1);

Synth(\snare, [\attack, 0.6, \release, 0.01, \amp, 10, \pan, -0.5])
Synth(\snare, [\attack, 0.001, \release, 0.1, \filter, 6000, \amp, 10, \pan, -0.5])
Synth(\snare, [\attack, 0.001, \release, 0.1, \filter, 1600, \amp, 10, \pan, 0])
Synth(\snare, [\attack, 0.001, \release, 2.1, \filter, 9000, \amp, 10, \pan, 0])

//////////////////////

// Communication OF / SuperCollider
// Déclaration des adresses des différents serveurs OSC
(
//a = NetAddr.new("127.0.0.1", 12000); // adresse du Serveur OSC de Processing
//b = NetAddr.new("127.0.0.1", 12001); // adresse du Serveur OSC de Pure Data
//c = NetAddr.new("127.0.0.1", 12002); // adresse du Serveur OSC de SuperCollider
//n = NetAddr.new("192.168.0.17", 12001); // adresse du Serveur OSC de OF
)


OSCFunc.trace(true);
OSCFunc.trace(false);



/* Le Seveur OSC de SuperCollider (totalement illisible)*/
(
o = OSCFunc({ arg msg, time, addr, recvPort;
	if(msg[0]=='/event',{
		//[msg, time, addr, recvPort].postln;
		msg[1].postln;
		'Match OK'.postln;
		x.set(\t_gate, 1);
	});
	}, '/event');
)


// Attention à bien libérer le serveur OSC quand on a fini
o.free;