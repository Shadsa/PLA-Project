package roles.conditions;

import cases.Case;
import cases.CaseProperty;
import cases.Construction;
import cases.Mur;
import cases.TypeCase;
import cases.TypeCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Type extends Condition {

	TypeCase _type;
	Cardinaux _direction;

	public Type(TypeCase type, Cardinaux card) {
		_type = type;
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		Case c = World.Case(destX, destY);
/*		System.out.println(c!=null);
		System.out.println(c.type() instanceof Construction);
		if(c!=null && c.type() instanceof Construction){
		System.out.println(c!=null && target.owner() != ((Construction) c.type()).getOwner());
		System.out.println(target.owner());
		System.out.println(((Construction) c.type()).getOwner());
		}
		System.out.println("=============================");
*/


		if(c!=null && c.type() instanceof Construction && target.owner() != ((Construction) c.type()).getOwner()){
			System.out.println(target.owner());
			System.out.println(((Construction) c.type()).getOwner());
			CaseProperty p = new TypeCheck(_type);
			return p.check(c);
		}
		else
			if(c!=null && c.type().value()!=Mur._id){
				CaseProperty p = new TypeCheck(_type);
				return p.check(c);			
			}
			else
				return false;
	}
}
