package tann.village.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class Sounds {


	public static AssetManager am= new AssetManager();

	public static void setup(){
		//sfx//
		makeSound("sfx/loaded.ogg", Sound.class);
		makeSound("sfx/point.ogg", Sound.class);
		
		makeSound("sfx/snake_enemycrash.ogg", Sound.class);
		makeSound("sfx/snake_playercrash.ogg", Sound.class);
		makeSound("sfx/snake_move.ogg", Sound.class);
		
		makeSound("sfx/space_shoot.ogg", Sound.class);
		makeSound("sfx/space_stationhit.ogg", Sound.class);
		makeSound("sfx/space_warp.ogg", Sound.class);
		makeSound("sfx/space_asteroidkill.ogg", Sound.class);
		
		makeSound("sfx/turtle_die.ogg", Sound.class);
		makeSound("sfx/turtle_duck.ogg", Sound.class);
		makeSound("sfx/turtle_jump.ogg", Sound.class);
		
		//music//
		makeSound("music/turtle.ogg", Music.class);
		makeSound("music/snake.ogg", Music.class);
		makeSound("music/space.ogg", Music.class);
		
		//stuff to attempt to load sounds properly//
		am.finishLoading();
		Array<Sound> sounds = new Array<Sound>();
		am.getAll(Sound.class, sounds);
		for(Sound s:sounds)s.play(0);
		Array<Music> musics = new Array<Music>();
		am.getAll(Music.class, musics);
		for(Music m:musics){
			m.play();
			m.setVolume(1);
			m.stop();
		}
	}
	
	public static <T> T get(String name, Class<T> type){
		String folder = type==Sound.class?"sfx":"music";
		name=folder+"/"+name;
		if(type==Sound.class) name=name+".ogg";
		if(type==Music.class) name=name+".ogg";
		return am.get(name, type);
	}
	
	private static void makeSound(String path, Class type){
		am.load(path, type);
	}
	
	private static ArrayList<Fader> faders = new ArrayList<Sounds.Fader>();
	
	public static void fade(Music m, float targetVolume, float duration){
		faders.add(new Fader(m, targetVolume, duration));
	}
	
	public static void tickFaders(float delta){
		for(int i=faders.size()-1;i>=0;i--){
			Fader f = faders.get(i);
			f.tick(delta);
			if(f.done)faders.remove(f);
		}
	}
	
	private static Music previousMusic;
	private static Music currentMusic;
	public static void playMusic(Music m){
		previousMusic=currentMusic;
		if(previousMusic!=null)previousMusic.stop();
		currentMusic=m;
		currentMusic.play();
		currentMusic.setLooping(true);
		updateMusicVolume();
	}
	
	public static void updateMusicVolume(){
		if(currentMusic!=null)currentMusic.setVolume(Slider.music.getValue());
	}
	
	static class Fader{
		float startVolume;
		float targetVolume;
		Music music;
		boolean done;
		float duration;
		float ticks;
		public Fader(Music m, float targetVolume, float duration) {
			this.startVolume=m.getVolume();
			this.targetVolume=targetVolume;
			this.music=m;
			this.duration=duration;
		}
		public void tick(float delta){
			ticks+=delta;
			if(ticks>duration){
				ticks=duration;
				done=true;
			}
			float ratio = ticks/duration;
			float newVolume =startVolume+(targetVolume-startVolume)*ratio;
			music.setVolume(newVolume);
		}
	}

	static HashMap<String, Sound> soundMap = new HashMap<String, Sound>();
	public static void playSound(String string) {
		Sound s = soundMap.get(string);
		if(s==null){
			s=get(string, Sound.class);
			soundMap.put(string, s);
		}
		s.play(Slider.SFX.getValue());		
	}
}
