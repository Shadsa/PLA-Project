package cases;

import roles.Personnage;
import roles.World;

public class LibreCheck implements CaseProperty {

	Personnage _pers;
	
	public LibreCheck(Personnage pers){
		_pers = pers;
	}

	@Override
	public boolean check(Case c) {
		return c != null && c.isfree() && (!(c.type() instanceof Arbre) || _pers.classe().hard_walker()
				&& (!(c.type() instanceof Mur) || _pers.owner() == ((Construction) c.type()).getOwner()));
	}

}
