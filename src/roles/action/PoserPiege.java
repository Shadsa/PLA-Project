package roles.action;

import cases.Piege;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public class PoserPiege extends Action {

		Cardinaux _direction;

		private static int _Id = Action.getId(9);

		public PoserPiege(Cardinaux card) {
			super();
			_direction = card;
		}

		@Override
		public void Act(World world, Personnage pers) {
			int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
			int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
			world.modifierCase(new Piege(pers), destX, destY);
			pers.owner().joueur().changerRessource(-250);
			pers.setState(new States(Statut.ATTAQUE, _direction));
		}

		@Override
		public int toInt() {
			return _Id;
		}

}
