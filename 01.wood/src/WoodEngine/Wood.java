package WoodEngine;
import java.util.HashSet;

public class Wood implements IWood {
	/**
	 * 0 - floor
	 * 1 - wall
	 * 2 - trap
	 * 3 - life
	 */
	private char[][] m_woodMap;
	private HashSet<Woodman> m_woodmansSet;
	
	private void eraseWoodman(String name){
		for (Woodman curWM : m_woodmansSet) {
			if (curWM.GetName() == name) {
				m_woodmansSet.remove(curWM);
			}
		}
	}
	
	public Wood (Character[] objs, int height, int width){
		m_woodmansSet = new HashSet<Woodman>();
		m_woodMap = new char[width][height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				m_woodMap[j][i] = objs[i*width + j];
			}
		}
	}

	@Override
	public void createWoodman(String name, Point start) throws Exception {
		for (Woodman curWM : m_woodmansSet) {
			if (curWM.GetName() == name) {
				throw new Exception("Cannot create new Woodman with '" + name + "' name. It's already in use.");
			}
		}
		if(m_woodMap[start.getX()][start.getY()] != '0'){
			throw new Exception("Bad start point");
		}
		m_woodmansSet.add(new Woodman(name, start));
	}

	@Override
	public Action move(String name, Direction direction) {
		for (Woodman curWM : m_woodmansSet) {
			if (curWM.GetName() == name) {
				Point curLoc = curWM.GetLocation();
				Point wannabeLoc = curLoc.MoveTo(direction);
				switch(m_woodMap[wannabeLoc.getX()][wannabeLoc.getY()]){
				case '1': return Action.Fail; // wall
				case '0': { // floor
					curWM.SetLocation(wannabeLoc);
					return Action.Ok;
				}
				case '2': { // trap
					curWM.SetLocation(wannabeLoc);
					if (curWM.Kill()) {
						eraseWoodman(name);
						return Action.WoodmanNotFound;
					}
					return Action.Dead;
				}
				case '3': { // life
					curWM.SetLocation(wannabeLoc);
					curWM.AddLife();
					return Action.Life;
				}
				default: return Action.Fail;
				}
			}
		}
		return Action.WoodmanNotFound;
	}

}