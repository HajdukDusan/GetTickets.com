Vue.component("worker-nav", {
  template: `
    
    
    <div>
        <link rel="stylesheet" href="css/home.css" type="text/css">
        <b-navbar toggleable="lg" type="dark" variant="dark">
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-worker">Home</b-nav-item>
            <b-nav-item href="#/dodaj-manifestaciju">Kreiraj manifestaciju</b-nav-item>
            <b-nav-item href="#/home-korisnik">Vase Manifestacije</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">

            <b-nav-item href="#/edit-profil" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout"right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      </div>
      `,

  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
});
