package com.yourname.spacecleaner.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.yourname.spacecleaner.objects.ShipObject;
import com.yourname.spacecleaner.objects.BulletObject;
import com.yourname.spacecleaner.objects.TrashObject;
import com.yourname.spacecleaner.GameSettings;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        // Столкновение пули с мусором
        if ((userDataA instanceof BulletObject && userDataB instanceof TrashObject) ||
            (userDataA instanceof TrashObject && userDataB instanceof BulletObject)) {

            BulletObject bullet = (userDataA instanceof BulletObject) ?
                (BulletObject) userDataA : (BulletObject) userDataB;
            TrashObject trash = (userDataA instanceof TrashObject) ?
                (TrashObject) userDataA : (TrashObject) userDataB;

            if (bullet.isAlive() && trash.isAlive()) {
                bullet.setAlive(false);
                trash.setAlive(false);
            }
        }

        // Столкновение корабля с мусором
        if ((userDataA instanceof ShipObject && userDataB instanceof TrashObject) ||
            (userDataA instanceof TrashObject && userDataB instanceof ShipObject)) {

            ShipObject ship = (userDataA instanceof ShipObject) ?
                (ShipObject) userDataA : (ShipObject) userDataB;
            TrashObject trash = (userDataA instanceof TrashObject) ?
                (TrashObject) userDataA : (TrashObject) userDataB;

            if (ship.isAlive() && trash.isAlive()) {
                ship.hit();
                trash.setAlive(false);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
