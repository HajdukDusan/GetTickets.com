const HomePage = { template: "<home></home>" };
const Registracija = { template: "<registracija></registracija>" };
const Login = { template: "<login></login>" };
const HomeAdmin = { template: "<home-admin></home-admin>" };
const HomeWorker = { template: "<home-worker></home-worker>" };
const HomeKorisnik = { template: "<home-korisnik></home-korisnik>" };
const EditProfil = { template: "<edit-profil></edit-profil>" };
const PregledKorisnika = {
  template: "<pregled-korisnika></pregled-korisnika>",
};
const PrikazManifestacije = {
  template: "<prikaz-manifestacije></prikaz-manifestacije>",
};
const DodajManifestaciju = {
  template: "<dodaj-manifestaciju></dodaj-manifestaciju>",
};
const DefaultNav = { template: "<default-nav></default-nav>" };
const AdminNav = { template: "<admin-nav></admin-nav>" };
const WorkerNav = { template: "<worker-nav></worker-nav>" };
const KorisnikNav = { template: "<korisnik-nav></korisnik-nav>" };
const router = new VueRouter({
  mode: "hash",
  routes: [
    { path: "/", component: HomePage },
    { path: "/registracija", component: Registracija },
    { path: "/login", component: Login },
    { path: "/home-korisnik", component: HomeKorisnik },
    { path: "/edit-profil", component: EditProfil },
    { path: "/prikaz-manifestacije", component: PrikazManifestacije },
    { path: "/korisnik-nav", component: KorisnikNav },
    { path: "/default-nav", component: DefaultNav },
    { path: "/admin-nav", component: DefaultNav },
    { path: "/worker-nav", component: DefaultNav },
    { path: "/home-admin", component: HomeAdmin },
    { path: "/home-worker", component: HomeWorker },
    { path: "/pregled-korisnika", component: PregledKorisnika },
    { path: "/dodaj-manifestaciju", component: DodajManifestaciju },
  ],
});

var app = new Vue({
  router,
  el: "#app",
});
