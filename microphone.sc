s.boot;                                           //===OctaGon Radio====//
s = -3  \\db
//
( // SynthDefs All Lines without Equalizer
SynthDef("\microphone", { arg out= 0 ; Out.ar(out,  AudioIn.ar([1,3]) ) }).writeDefFile;
SynthDef("\epistrofi", { arg out= 2 ; Out.ar(out, AudioIn.ar([ 11,12])); }).writeDefFile;
SynthDef("player", { arg out= 0 ; Out.ar(out, AudioIn.ar([ 5,6])); }).writeDefFile;
SynthDef("\pulse", { arg out= 0 ; Out.ar(out, AudioIn.ar([ 7,8])); }).writeDefFile;
SynthDef("\mamathron", { arg out= 0 ; Out.ar(out, AudioIn.ar([ 9,10])); }).writeDefFile;
SynthDef("\Instrument", { arg out= 0 ; Out.ar(out, AudioIn.ar([ 2,4])); }).writeDefFile;
)
//
(// Base ConnectionS Inputs Outputs with  Mic Octagon Mamathron Player   
( // SynthDef's of OctaGon Radio  with Equalizer
( // SynthDef Return or feadback with Equalizer6
SynthDef ("\return", { arg out= 2 ;
	var def6, eq6 , eqreturn;
def6 = EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq6 = EQSetting( def6);
	eqreturn= { eq6.ar  (AudioIn.ar([11,12]), \eqSetting.kr(eq6.setting))};
Out.ar(out, eqreturn);
}).writeDefFile;
)
( // SynthDef Mic with Equalizer3
SynthDef ("\mic", { arg out= 0 ;
	var def3, eq3, eqmic;
def3 = EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq3 = EQSetting( def3 );
eqmic = { eq3.ar  (AudioIn.ar([1,3]), \eqSetting.kr(eq3.setting))};
	Out.ar(out, eqmic);
}).writeDefFile;
)
( // SynthDef SecondIn with Equalizer7
SynthDef ("\secondin", { arg out= 0 ;
	var def7, eq7, eqsecond;
	def7= EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq7 = EQSetting( def7 );
eqsecond = { eq7.ar  (AudioIn.ar([2,4]), \eqSetting.kr(eq7.setting))};
	Out.ar(out, eqsecond);
}).writeDefFile;
)
( // SynthDef Player with Equalizer2
SynthDef ("\player", { arg out= 0 ;
	var def2, eq2 , eqplayer;
def2 = EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq2 = EQSetting( def2 );
eqplayer = { eq2.ar  (AudioIn.ar([5,6]), \eqSetting.kr(eq2.setting))};
Out.ar(out, eqplayer);
}).writeDefFile;
)
( // SynthDef Browser  with Equalizer4
SynthDef ("\browser", { arg out= 0 ;
	var def4, eq4 , eqbrowser;
def4 = EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq4 = EQSetting( def4 );
eqbrowser = { eq4.ar  (AudioIn.ar([7,8]), \eqSetting.kr(eq4.setting))};
Out.ar(out, eqbrowser);
}).writeDefFile;
)
( // SynthDef Mamathron  with Equalizer5
SynthDef ("\mamathron", { arg out= 0 ;
	var def5, eq5 , eqmamathron;
def5 = EQdef( *({|i| [ ("peak" ++ i).asSymbol,BPeakEQ ] }!20).flatten(1) ); //  20-bands parametric eq 
eq5 = EQSetting( def5 );
eqmamathron = { eq5.ar  (AudioIn.ar([9,10]), \eqSetting.kr(eq5.setting))};
Out.ar(out, eqmamathron);
}).writeDefFile;
)
)
(// MAIN Stage
( //Microphone && Monitor it
~micmonitor = Synth ("\mic", [\out, 2] );
~micmonitor.free; ~micmonitor.release;
~mic = Synth ("\mic" );
~mic.free; ~mic.release;
~view1.action = { |view, setting| ~mic.set( \eqSetting, setting ) };
~view1 = EQView(eqSetting:  ~eq3);
)
(  // Octagon Feadback or Return 
~return = Synth ("\return",[\out, 2]);
~return1 = Synth ("\return",[\out, 3]);
~return.free; ~return1.free;
~view2.action = { |view, setting| ~mic.set( \eqSetting, setting ) };
~view2 = EQView(eqSetting:  ~eq6);
)
( // vlc or player to octagon
~vlc = Synth("\player");
~vlc.free; ~vlc.release;
~monitorVlc=Synth("\vlc",[\out, 2]);
~montorVlc.free; ~monitorVlc.reslease; 
~view3.action = { |view, setting| ~vlc.set( \eqSetting, setting ) };
~view3 = EQView(eqSetting:  ~eq2);
)
( // Browser
~browser = Synth("\browser");
~browser.free; ~browser.release;
~view4.action = { |view, setting| ~browser.set( \eqSetting, setting ) };
~view4 = EQView(eqSetting:  ~eq4);
)
(//Manathron
~mamathron = Synth("mamathron");
~mamathron.free; ~mamathron.release;
~view5.action = { |view, setting| ~mamathron.set( \eqSetting, setting ) };
~view5 = EQView(eqSetting:  ~eq5);
)
( //Send to Mamathron
// Mic to mamathron
~mamathronmic = Synth ("\mic", [\out, 4 ]);
~mamathronmic.free; ~mamthronmic.release;
//monitor to mamathron
~mamathronMonitor = Synth ("\return ", [\out, 4 ]);
~mamathronMonitor.free; ~mamathronMonitor.release;
// vlc to mamathron
~mamathronVlc = Synth("\player", [\out, 4 ]);
 ~mamathronVlc.free; ~mamathronVlc.release;
// pulse to mamathron
~mamathronBrowser = Synth ("\browser", [\out, 4 ]);
~mamathronBrowser.free; ~mamathronBrowser.release;
)
( //SecondIn
~secondIn = Synth ("\secondin");
~secondIn.free; ~secondIn.release;
~view6.action = { |view, setting| ~vlc.set( \eqSetting, setting ) };
~view6 = EQView(eqSetting:  ~eq7);
)

)
ULib.startup;
UChain.gui