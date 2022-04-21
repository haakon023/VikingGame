package group22.viking.game.controller.firebase;

import com.badlogic.gdx.Preferences;

import org.junit.Assert;


public class ProfileCollectionTest {

    private ProfileCollection profileCollection;

    // Test data
    Profile profile = null;
    String profileId = null;
    String name = "fooBAR";
    int avatarId = 1;

    String profileIdHost = "000000000000000000001";
    String profileIdGuest = "000000000000000000002";

    public ProfileCollectionTest(FirebaseInterface firebaseInterface){
        //profileCollection = new ProfileCollection(firebaseInterface);
    }


    public void createProfileTest() {
        profileCollection.createProfile(name, avatarId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profile = (Profile) document;
                Assert.assertEquals(profile.getName(), name);
                Assert.assertEquals(profile.getAvatarId(), avatarId);
                Assert.assertEquals(profile.getLostGames(), 0L);
                Assert.assertEquals(profile.getWonGames(), 0L);
                //Assert.assertEquals(profile.getScore(), 0.0);
                profileId = profile.getId();
            }

            @Override
            public void onFailure() {
                Assert.assertTrue(false);
            }
        });
    }

    public void readProfileTest() {
        profileCollection.readProfile(profileId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profile = (Profile) document;
                Assert.assertEquals(profile.getName(), name);
                Assert.assertEquals(profile.getAvatarId(), avatarId);
            }

            @Override
            public void onFailure() {
                Assert.assertTrue(false);
            }
        });
    }

    /*public void addFinishedGameTest() {
        profileCollection.addFinishedGame(profile, true, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profile = (Profile) document;
                Assert.assertEquals(profile.getName(), name);
                Assert.assertEquals(profile.getAvatarId(), avatarId);
                Assert.assertEquals(profile.getLostGames(), 0L);
                Assert.assertEquals(profile.getWonGames(), 1L);
                //Assert.assertEquals(profile.getScore(), 0.0); // TODO
            }

            @Override
            public void onFailure() {
                Assert.assertTrue(false);
            }
        });
    }*/

    public void setHostIdTest() {
        profileCollection.readProfile(profileIdHost, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profileCollection.setHostId(profileIdHost);
                Assert.assertEquals(profileCollection.getHostProfile().getId(), profileIdHost);
            }

            @Override
            public void onFailure() {
                Assert.assertTrue(false);
            }
        });
    }

    public void setGuestIdTest() {
        profileCollection.readProfile(profileIdGuest, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profileCollection.setGuestId(profileIdGuest);
                Assert.assertEquals(profileCollection.getGuestProfile().getId(), profileIdGuest);
            }

            @Override
            public void onFailure() {
                Assert.assertTrue(false);
            }
        });
    }

}
