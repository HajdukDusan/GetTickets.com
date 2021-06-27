Vue.component("home-korisnik", {
  data: function () {
    return {
      cookie: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
  },

  template: `<div>
    
      <korisnik-nav></korisnik-nav>
    </div>`,
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
});
